package evaluatorSpringBoot.services.docker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

import evaluatorSpringBoot.Config;
import evaluatorSpringBoot.core.poo.Submission;
import evaluatorSpringBoot.services.LogSystem;

public class MyDockerClientImpl implements MyDockerClient {
  private ApplicationContext context  = new AnnotationConfigApplicationContext(Config.class);
	private final String volume_path    = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/submissions";
	private final String dockerFilePath = "/codeEvaluator/src/main/resources/docker/Dockerfile";

	private String       imageId;
	private String       containerId;
	private DockerClient client;
	private LogSystem    logs;

	public MyDockerClientImpl() {
		this.client = DockerClientBuilder.getInstance().build();
		logs        = context.getBean(LogSystem.class);
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

    String targetFile = "/app/submissions/" + newSubmission.getStdoutPath();

    CreateContainerResponse container = this.client.createContainerCmd(this.imageId)
      .withBinds(new Bind(volume_path, new Volume("/app/submissions")))
      .withCmd("bash", targetFile)
      .exec();

		this.containerId = container.getId();
	}

	@Override
	public void startContainer(String containerId) {
		this.client.startContainerCmd(containerId).exec();
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
					.withStdErr(true)
					.withTailAll()
					.exec(collectFramesCallback).awaitCompletion();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    String result = collectFramesCallback.frames.toString().replaceAll("STDOUT: ", "");

    logs.addLog("Get evaluator logs:  " + result);

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

		logs.addLog("Get image ids:" + imageId);

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
