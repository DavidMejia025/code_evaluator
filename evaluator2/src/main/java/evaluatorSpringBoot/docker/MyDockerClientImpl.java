package evaluatorSpringBoot.docker;
//./StarUML-3.1.0-x86_64.AppImage
//chmod a+x StarUML-3.1.0-x86_64.AppImage

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;

import evaluatorSpringBoot.poos.Submission;

public class MyDockerClientImpl implements MyDockerClient {
	private final String volume_path     = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2";
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
		Volume volume1    = new Volume("/app");
        String targetFile =  "app" + newSubmission.getStdoutPath(); 
         
	    CreateContainerResponse container = this.client.createContainerCmd("e93b746e985b")
			      .withVolumes(volume1)
			      .withBinds(new Bind(volume_path, volume1))
			      .withCmd("ruby", targetFile)
			      .exec();
			      			     
		System.out.println("create container");
		System.out.println(targetFile);
		
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
			pullImage(client, imageName);
			//get the image id from the list of images
			imageId = findImageId(client, imageName);
		}
		
		return imageId;
	}
	
	public void pullImage(DockerClient client, String name) {
		// Verify if image already exist to not pull every time.
		//* image contains an attribute repoTags={ruby:1-slim} it will require some regex or similar tool
		//* How to get the image id from the pull.
		client.pullImageCmd("ruby").exec(new PullImageResultCallback());
	}
	
	private String findImageId(DockerClient client, String name) {
		List<Image> images = client.listImagesCmd().exec();

		for(int i=0; i < images.size(); i++){
		    System.out.println(images);
		}
		//List<SearchItem> dockerSearch = dockerClient.searchImagesCmd("ruby").exec();
		//System.out.println("Search returned" + dockerSearch.toString());
		
        return images.toString();
	}
	
	public String buildImage(DockerClient client, String dockerFilePath) {
		String imageId = client.buildImageCmd(new File(dockerFilePath)).withNoCache(true)
			    	      .exec(new BuildImageResultCallback()).awaitImageId();
		return imageId;
	}
}
