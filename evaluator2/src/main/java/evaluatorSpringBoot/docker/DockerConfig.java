package evaluatorSpringBoot.docker;

import com.github.dockerjava.api.DockerClient;

public interface DockerConfig {
	void configClient(DockerClient client);
	
	String getImageId(DockerClient client, String method);
	
	void pullImage(DockerClient client, String name);
	
	String buildImage(DockerClient client, String dockerFile);
}
