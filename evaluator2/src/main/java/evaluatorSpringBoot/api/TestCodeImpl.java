package evaluatorSpringBoot.api;

import org.json.JSONObject;

import evaluatorSpringBoot.api.poos.Response;
import evaluatorSpringBoot.services.file_system.FileSystemImpl;

public class TestCodeImpl implements TestCode {
	private String params;
	
	public TestCodeImpl(String params) {
        this.params = params;
    }
	@Override
	public Response runEval() {
		JSONObject obj  = new JSONObject(this.params);
		String     params = obj.getString("code");
		 
		new FileSystemImpl().createTestFile(params);
		System.out.println(params);
        return new Response(1,
                params);
    }
}