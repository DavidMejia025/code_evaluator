package evaluatorSpringBoot.services.docker;

import com.github.dockerjava.api.DockerClient;

public interface MyDockerClient {
	DockerClient buildClient();
	
	void createContainer();
	
	void startContainer(String containerId);
	
	void stopContainer(String containerId);
	
	void removeContainer(String containerId);
	
	String getLogs(String containerId);
}
