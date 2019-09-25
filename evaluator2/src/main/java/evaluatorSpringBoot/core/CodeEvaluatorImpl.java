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
import org.jvnet.hk2.annotations.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.common.io.Files;

import evaluatorSpringBoot.Config;
import evaluatorSpringBoot.core.poo.Response;
import evaluatorSpringBoot.core.poo.Submission;
import evaluatorSpringBoot.persistance.dao.ResponseDao;
import evaluatorSpringBoot.persistance.dao.SubmissionDao;
import evaluatorSpringBoot.services.LogSystem;
import evaluatorSpringBoot.services.docker.MyDockerClientImpl;

@Service
public class CodeEvaluatorImpl implements CodeEvaluator {
  private ApplicationContext context         = new AnnotationConfigApplicationContext(Config.class);
	private final String       basePath        = "/codeEvaluator/submissions/";
	private final String       stdoutFilePath  = "/codeEvaluator/submissions/documents/stdout_test.rb";
	private SubmissionDao      submissionDAO;
	private ResponseDao        responseDAO;
	private String             submissionInput;
	private String             testPath;
	private LogSystem          logs;

	public CodeEvaluatorImpl(String submissionInput) {
	  this.submissionInput = submissionInput;
	  submissionDAO        = context.getBean("SubmissionDaoBean",SubmissionDao.class);
	  responseDAO          = context.getBean("ResponseDaoBean", ResponseDao.class);
	  logs                 = context.getBean("LogsBean",LogSystem.class);

	  //this.submissionDAO   = FactoryDao.createSubmission(); // string injector can avoid this line of code
	  //this.responseDAO     = FactoryDao.createResponse();
  }

	@Override
	public Response runEval() throws IOException{
	  logs.addLog("Submision received with input:" + this.submissionInput);
		String code = parseJson(this.submissionInput);

		Submission newSubmission = new Submission(code);
		logs.addLog("Create new submission record .. ----->>>>>.................................: ");
		submissionDAO.create(newSubmission);

		prepare(newSubmission);

		String result = runTest(newSubmission);

		cleanTest(newSubmission);

	  Response newResponse = new Response(newSubmission.getSubmissionId(), result, 200);
	  responseDAO.create(newResponse);

    return newResponse;
    }

	private void prepare(Submission newSubmission) throws IOException{
		createSubmissionFolder(newSubmission);
		createTestFile(newSubmission);
		copyStdoutFile(newSubmission);
	}

	private String runTest(Submission newSubmission) {
		MyDockerClientImpl dockerClient = new MyDockerClientImpl();

		dockerClient.createContainer(newSubmission);
		dockerClient.startContainer(dockerClient.getContainerId());

		String dockerLogs = dockerClient.getLogs(dockerClient.getContainerId());

		createResultFile(newSubmission, dockerLogs);;

		return dockerLogs;
	}

	private void cleanTest(Submission newSubmission) {
		String path = testPath;
		File dir    = new File(path);

		deleteDirectory(dir);
	}

	private String parseJson(String json) {
		JSONObject obj = new JSONObject(json);

		String jsonParsed = obj.getString("code");

		return jsonParsed;
	}

	private void createSubmissionFolder(Submission newSubmission) {
	  testPath = basePath + Integer.toString(newSubmission.getSubmissionId());
		File newFolder = new File(testPath);

    boolean created =  newFolder.mkdir();
	}

	public void createTestFile(Submission newSubmission) {
		String fileUrl = testPath + "/" + "user_source_code.rb";
		File new_file  = new File(fileUrl);

		String code = newSubmission.getCode();

    String fileData = "def test;" + code + ";"+ "end";
    generateFOS(new_file, fileData);
  }

  public void createResultFile(Submission newSubmission, String body) {
  	String resultUrl = testPath + "/" + "result.txt";
  	File new_file    = new File(resultUrl);

  	String fileData = "Result of the code evaluation is \n" + body + "\n";
    generateFOS(new_file, fileData);
  }

	public String readTestFile(Submission newSubmission){
		String resultUrl = testPath + "/" + "result.txt";
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
      }

		return result;
	}

	private void copyStdoutFile(Submission newSubmission) throws IOException{
		String from = stdoutFilePath;
		String to   = testPath + "/" + "stdout_test.rb";

		Path srcFilePath  = Paths.get(from);
    Path destFilePath = Paths.get(to);

    Files.copy(srcFilePath.toFile(), destFilePath.toFile());
		System.out.println("!!!!!!!!!!!!!!!! " + destFilePath);

    newSubmission.setStdoutPath(Integer.toString(newSubmission.getSubmissionId()) + "/" + "stdout_test.rb"); //Think twice this route
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
