package evaluatorSpringBoot.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

import evaluatorSpringBoot.docker.MyDockerClientImpl;
import evaluatorSpringBoot.poos.Response;
import evaluatorSpringBoot.poos.Submission;

public class CodeEvaluatorImpl implements CodeEvaluator {
	private final String result_path     = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/submissions/result.txt";
    private final String user_souce_code = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/submissions/user_source_code.rb";
	private String params;
	
	public CodeEvaluatorImpl(String params) {
        this.params = params;
    }
	@Override
	public Response runEval() {
		String params = parseJson(this.params);
		
		Submission newSubmission =  new Submission(1, params);
		
		createTestFile(params);
		
		Response submissionResult = runTest();
		
        return submissionResult;
    }
	
	private Response runTest() {
		MyDockerClientImpl dockerClient = new MyDockerClientImpl();
		
		dockerClient.createContainer();
		dockerClient.startContainer(dockerClient.getContainerId());
		
		String dockerLogs = dockerClient.getLogs(dockerClient.getContainerId());
		
		createResultFile(dockerLogs);
		
		Response submissionResult = new Response(1,"some code", dockerLogs);
		
		System.out.println(dockerClient.getContainerId());
		return submissionResult;
	}
	
	private String parseJson(String json) {
		JSONObject obj  = new JSONObject(json);
		
		String jsonParsed = obj.getString("code");
		
		return jsonParsed;
	}
	
	public void createTestFile(String body) {
		File new_file = new File(user_souce_code);
	    String fileData = "def test;" + body + ";"+ "end";
	    
	    generateFOS(new_file, fileData);
	    System.out.println(body);
    }
    
    public void createResultFile(String body) {
		File new_file = new File(result_path);
	    String fileData = "Result of the code evaluation is \n" + body + "\n";
	    
	    generateFOS(new_file, fileData);
	    System.out.println(body);
    }
	
	public String readTestFile(){
		String result = "";
	    String line = null;
	    
		try {
	    	FileReader fileReader = new FileReader(result_path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
	      
	        while((line = bufferedReader.readLine()) != null) {
	            System.out.println(line);
	            result += "-----" + line + "-----";
	        }  
	        
	        bufferedReader.close();  	      
	    }
	    catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + result_path + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + result_path + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
		
		return result;
	}
	
	private void generateFOS(File new_file, String fileData) {
		try {
			FileOutputStream fos = new FileOutputStream(new_file);
			
			fos.write(fileData.getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}