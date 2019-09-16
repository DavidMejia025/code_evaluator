package evaluatorSpringBoot.docker;
//./StarUML-3.1.0-x86_64.AppImage
//chmod a+x StarUML-3.1.0-x86_64.AppImage

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.SearchItem;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;

import evaluatorSpringBoot.poos.Submission;

public class MyDockerClientImpl implements MyDockerClient {
	//private final String volume_path    = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2";
	//private final String dockerFilePath = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/src/main/resources/docker/Dockerfile";
	private final String volume_path    = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2";
	private final String dockerFilePath = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/src/main/resources/docker/Dockerfile";
	public String imageId;
	public String containerId;
	public DockerClient client;
	
	public MyDockerClientImpl() {
		this.client = DockerClientBuilder.getInstance().build();
	}
	
	public DockerClient buildClient() {
		return DockerClientBuilder.getInstance().build(); 
	}
	
	public DockerClient getClient() {
		return this.client;
	}
	
	public String getContainerId() {
		return this.containerId;
	}
	
	@Override
	public void createContainer(Submission newSubmission) {
		this.imageId = getImageId(client, "build");
		
		Volume volume1    = new Volume("/app");
        String targetFile = "app" + newSubmission.getStdoutPath(); 
        
	    CreateContainerResponse container = this.client.createContainerCmd(this.imageId)
			      .withVolumes(volume1)
			      .withBinds(new Bind(volume_path, volume1))
			      .withCmd("ruby", targetFile)
			      .exec();
			      			     
		System.out.println("create container");
		System.out.println(targetFile);
		System.out.println(this.imageId);
		this.containerId = container.getId();
	}

	@Override
	public void startContainer(String containerId) {
		this.client.startContainerCmd(containerId)
	      .exec();
	}

	@Override
	public void stopContainer(String containerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeContainer(String containerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLogs(String containerId){
		FrameReaderITestCallback collectFramesCallback = new FrameReaderITestCallback();
	    
	     try {
			this.client.logContainerCmd(containerId)
				  .withFollowStream(true)
					.withStdOut(true)
					.withTailAll()
					.exec(collectFramesCallback).awaitCompletion();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	     System.out.println(collectFramesCallback.frames);
	     
	     return collectFramesCallback.frames.toString();
	}
	
	public static class FrameReaderITestCallback extends LogContainerResultCallback {

        public List<Frame> frames = new ArrayList<>();

        @Override
        public void onNext(Frame item) {
            frames.add(item);
            super.onNext(item);
        }
    }
	
	@Override
	public String getImageId(DockerClient client, String method) {
		String imageName = "ruby";
		
		if (method == "build") {
			imageId = buildImage(client, dockerFilePath);
		} else {
			if (findImageId(this.client, "rubyx") == ""){
				pullImage(this.client, "ruby:2.5.5");
		    }
			
			imageId = findImageId(this.client, "ruby");
		}
		
		return imageId;
	}
	
	public void pullImage(DockerClient client, String name) {
		System.out.println(client.pullImageCmd(name).exec(new PullImageResultCallback()));
	}
	
	private String findImageId(DockerClient client, String name) {
		List<Image> images  = client.listImagesCmd().exec();
		Pattern regexImage  = Pattern.compile(name);
		String imageIdFound = "";
	
		for(int i = 0; i < images.size(); i++){
			String[] repoTags  = images.get(i).getRepoTags();
			
			for (int j = 1; j <= repoTags.length; j++) {
				System.out.println(repoTags[j-1]);
				System.out.println(name);
				Matcher m = regexImage.matcher(repoTags[j-1]);
				
				if (m.find()) {
					imageIdFound = images.get(i).getId().replaceAll("sha256:", "");
					j = repoTags.length + 1;
					i = images.size();
				}
			}
		}
		//List<SearchItem> dockerSearch = client.searchImagesCmd("alpine").exec();
		//System.out.println("Search returned" + dockerSearch.toString());

        return imageIdFound;
	}
	
	public String buildImage(DockerClient client, String dockerFilePath) {
		String imageId = client.buildImageCmd(new File(dockerFilePath))
				.withNoCache(true)
			    .exec(new BuildImageResultCallback())
			    .awaitImageId();
		
		return imageId;
	}
}
