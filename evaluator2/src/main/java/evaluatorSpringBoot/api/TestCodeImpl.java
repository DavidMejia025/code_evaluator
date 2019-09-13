package evaluatorSpringBoot.api;

import org.json.JSONObject;

import evaluatorSpringBoot.api.poos.Response;
import evaluatorSpringBoot.api.poos.Submission;
import evaluatorSpringBoot.services.docker.MyDockerClientImpl;
import evaluatorSpringBoot.services.file_system.FileSystemImpl;

public class TestCodeImpl implements TestCode {
	private String params;
	
	public TestCodeImpl(String params) {
        this.params = params;
    }
	@Override
	public Response runEval() {
		String params = parseJson(this.params);
		
		Submission newSubmission =  new Submission(1, params);
		
		new FileSystemImpl().createTestFile(params);
		
		Response submissionResult = runTest();
		
        return submissionResult;
    }
	
	private Response runTest() {
		MyDockerClientImpl dockerClient = new MyDockerClientImpl();
		
		dockerClient.createContainer();
		dockerClient.startContainer(dockerClient.getContainerId());
		
		String dockerLogs = dockerClient.getLogs(dockerClient.getContainerId());
		
		new FileSystemImpl().createResultFile(dockerLogs);
		
		Response submissionResult = new Response(1,"some code", dockerLogs);
		
		return submissionResult;
	}
	
	private String parseJson(String json) {
		JSONObject obj  = new JSONObject(json);
		
		String jsonParsed = obj.getString("code");
		
		return jsonParsed;
	}
}