package evaluatorSpringBoot.services.docker;
//./StarUML-3.1.0-x86_64.AppImage
//chmod a+x StarUML-3.1.0-x86_64.AppImage

import java.util.ArrayList;
import java.util.List;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;

public class MyDockerClientImpl implements MyDockerClient {
	private final String volume_path     = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2";
	private final String test_file_path  = "app/src/main/java/evaluatorSpringBoot/repository/stdout_test.rb";
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
	public void createContainer() {
		Volume volume1 = new Volume("/app");

	    CreateContainerResponse container = this.client.createContainerCmd("e93b746e985b")
			      .withVolumes(volume1)
			      .withBinds(new Bind(volume_path, volume1))
			      .withCmd("ruby",test_file_path)
			      .exec();
			      			     
		System.out.println("create container");
		
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
	
	
}