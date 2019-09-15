package evaluatorSpringBoot.docker;

import java.io.File;
import java.util.List;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;

//Perfect candidate for singleton?
public class DockerConfigImpl implements DockerConfig {
    public String imageId;
    private final String imageMethod = "build";
    private final String dockerFilePath = "src/test/resources";
    
	public DockerConfigImpl(String params) {
		DockerClient client = new MyDockerClientImpl().client;
		
		configClient(client);
		this.imageId = "e93b746e985b"; 
    }
	
	public String getImageId(){
		return this.imageId;
	}

	public void configClient(DockerClient client) {
		getImageId(client, imageMethod);
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