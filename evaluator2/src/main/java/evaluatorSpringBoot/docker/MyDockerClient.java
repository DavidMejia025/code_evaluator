package evaluatorSpringBoot.docker;

import com.github.dockerjava.api.DockerClient;

import evaluatorSpringBoot.poos.Submission;

public interface MyDockerClient {
	DockerClient buildClient();
	
	void createContainer(Submission newSubmission);
	
	void startContainer(String containerId);
	
	void stopContainer(String containerId);
	
	void removeContainer(String containerId);
	
	String getLogs(String containerId);
	
  String getImageId(DockerClient client, String method);
	
	void pullImage(DockerClient client, String name);
	
	String buildImage(DockerClient client, String dockerFile);
}
