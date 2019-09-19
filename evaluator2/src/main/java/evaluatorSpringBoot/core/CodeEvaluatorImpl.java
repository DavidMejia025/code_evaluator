package evaluatorSpringBoot.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;

import com.google.common.io.Files;

import evaluatorSpringBoot.api.CodeEvaluator;
import evaluatorSpringBoot.core.poo.Response;
import evaluatorSpringBoot.core.poo.Submission;
import evaluatorSpringBoot.persistance.dao.DaoTest;
import evaluatorSpringBoot.persistance.dao.SubmissionDao;
import evaluatorSpringBoot.persistance.dao.SubmissionFactoryDao;
import evaluatorSpringBoot.services.docker.MyDockerClientImpl;

public class CodeEvaluatorImpl  implements CodeEvaluator {
	private final String basePath   = "submissions/";
	private final String stdoutPath = "submissions/documents/stdout_test.rb";
	private final SubmissionDao submissionDAO;

	private String submissionInput;
	
	public CodeEvaluatorImpl(String submissionInput) {
	  this.submissionInput = submissionInput;
	  this.submissionDAO   = SubmissionFactoryDao.create();
  }
	
	@Override
	public Response runEval() throws IOException{
		String code = parseJson(this.submissionInput);
			
		Submission newSubmission = new Submission(code);
		this.submissionDAO.create(newSubmission);
		
		prepare(newSubmission);
		
		Response submissionResult = runTest(newSubmission);
		
		cleanTest(newSubmission);
		
    return submissionResult;
    }

	private void prepare(Submission newSubmission) throws IOException{
		createSubmissionFolder(newSubmission);
		createTestFile(newSubmission);
		copyStdoutFile(newSubmission);
	}
	
	private Response runTest(Submission newSubmission) {
		MyDockerClientImpl dockerClient = new MyDockerClientImpl();
		
		dockerClient.createContainer(newSubmission);
		dockerClient.startContainer(dockerClient.getContainerId());
		
		String dockerLogs = dockerClient.getLogs(dockerClient.getContainerId());
		
		createResultFile(newSubmission, dockerLogs);
		
		Response submissionResult = new Response(1,"some code", dockerLogs);
		
		//stop or delete container not implemented yet
		return submissionResult;
	}
	
	private void cleanTest(Submission newSubmission) {
		String path =  basePath + Integer.toString(newSubmission.getSubmissionId()) ;
		File dir    =  new File(path);
		
		deleteDirectory(dir);
	}
	
	private String parseJson(String json) {
		JSONObject obj  = new JSONObject(json);
		
		String jsonParsed = obj.getString("code");
		
		return jsonParsed;
	}
	
	private void createSubmissionFolder(Submission newSubmission) { 
		File newFolder = new File(basePath + Integer.toString(newSubmission.getSubmissionId()));
    
		//Create a exceotuin here:
    //try {
      boolean created =  newFolder.mkdir();
   // }catch{
     // System.out.println("Unable to create folder");
    //}
	}
	
	public void createTestFile(Submission newSubmission) {
		String fileUrl = basePath + Integer.toString(newSubmission.getSubmissionId()) + "/" + "user_source_code.rb";
		File new_file   = new File(fileUrl);
		
		String code = newSubmission.getCode();
		
    String fileData = "def test;" + code + ";"+ "end";
    generateFOS(new_file, fileData);
  }
    
  public void createResultFile(Submission newSubmission, String body) {
  	String resultUrl = basePath + Integer.toString(newSubmission.getSubmissionId()) + "/" + "result.txt";
	File new_file = new File(resultUrl);
    
	String fileData = "Result of the code evaluation is \n" + body + "\n"; 
    generateFOS(new_file, fileData);
  }
	
	public String readTestFile(Submission newSubmission){
		String resultUrl = basePath + Integer.toString(newSubmission.getSubmissionId()) + "/" + "result.txt";
		String result    = "";
	  String line      = null;
	    
		try {
	    FileReader fileReader = new FileReader(resultUrl);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
	      
      while((line = bufferedReader.readLine()) != null) {
          result += "-----" + line + "-----";
      }  
	        
	      bufferedReader.close();  	      
	    }
	    catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + resultUrl + "'");                
      }
      catch(IOException ex) {
          System.out.println("Error reading file '" + resultUrl + "'");                  
          // Or we could just do this: 
          // ex.printStackTrace();
      }
		
		return result;
	}
	
	private void copyStdoutFile(Submission newSubmission) throws IOException{
		String from = stdoutPath;
		String to   = basePath + Integer.toString(newSubmission.getSubmissionId()) + "/" + "stdout_test.rb";
		
		Path srcFilePath  = Paths.get(from);
    Path destFilePath = Paths.get(to);
    
    Files.copy(srcFilePath.toFile(), destFilePath.toFile());
    
    newSubmission.setStdoutPath("/submissions/" + Integer.toString(newSubmission.getSubmissionId()) + "/" + "stdout_test.rb");
	}
	
	public static boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				deleteDirectory(children[i]);
			}
		}

		return dir.delete();
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