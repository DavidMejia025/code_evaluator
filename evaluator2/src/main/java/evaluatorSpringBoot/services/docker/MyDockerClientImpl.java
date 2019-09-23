package evaluatorSpringBoot.services.docker;

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
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;

import evaluatorSpringBoot.core.poo.Submission;
import evaluatorSpringBoot.utils.Paths;

public class MyDockerClientImpl implements MyDockerClient {
	private final String volume_path    = Paths.CurrDirectory();
	private final String dockerFilePath = "src/main/resources/docker/Dockerfile";
	
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
    //String targetFile = "/app" + newSubmission.getStdoutPath(); 
    String targetFile = "stdout_test.rb"; 

    CreateContainerResponse container = this.client.createContainerCmd("121862ceb25f")
		      .withVolumes(volume1)
		      .withBinds(new Bind(volume_path, volume1))
          .withCmd("/bin/ls", "/app")
		      .exec();
    System.out.println("Get container.getId()///.......................................///");
    System.out.println(container.getId());
		this.containerId = container.getId();
	}

	@Override
	public void startContainer(String containerId) {
		this.client.startContainerCmd(containerId)
	      .exec();
	}

	@Override
	public void stopContainer(String containerId) {
		//Not implemented yet
		
	}

	@Override
	public void removeContainer(String containerId) {
	//Not implemented yet
		
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
			e.printStackTrace();
		}
	  
    String result = collectFramesCallback.frames.toString().replaceAll("STDOUT: ", "");
    result        = result.replaceAll("'", "\'");
    System.out.println("Get logs ///.......................................///");
    System.out.println(result);
    
    return result;
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
		System.out.println("Get image id///.......................................///");
		System.out.println(imageId);
		return imageId;
	}
	
	public void pullImage(DockerClient client, String name) {
		client.pullImageCmd(name).exec(new PullImageResultCallback());
	}
	
	private String findImageId(DockerClient client, String name) {
		List<Image> images  = client.listImagesCmd().exec();
		Pattern regexImage  = Pattern.compile(name);
		String imageIdFound = "";
	
		for(int i = 0; i < images.size(); i++){
			String[] repoTags  = images.get(i).getRepoTags();
			
			for (int j = 1; j <= repoTags.length; j++) {
				Matcher m = regexImage.matcher(repoTags[j-1]);
				
				if (m.find()) {
					imageIdFound = images.get(i).getId().replaceAll("sha256:", "");
					j = repoTags.length + 1;
					i = images.size();
				}
			}
		}

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
