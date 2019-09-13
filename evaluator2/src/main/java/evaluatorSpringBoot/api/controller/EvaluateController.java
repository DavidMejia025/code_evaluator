package evaluatorSpringBoot.api.controller;
/* Http request:
 * params = {code: "puts 'Hello world from Docker Yeah!!!'"}.to_json
 * headers = {"Content-Type"=>"application/json"}
 * response = HTTParty.post('http://localhost:8080/cheers', body: params, headers: headers)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import evaluatorSpringBoot.api.poos.Code;

@RestController
public class EvaluateController {
  private DockerClient dockerClient;
  private final AtomicLong counter     = new AtomicLong();
  private final String result_path     = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/src/main/java/evaluatorSpringBoot/repository/result.txt";
  //private final String result_path     = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/src/main/java/evaluatorSpringBoot/repository/result.txt";
  private final String volume_path     = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2";
  private final String test_file_path  = "app/src/main/java/evaluatorSpringBoot/repository/stdout_test.rb";
  private final String user_souce_code = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/src/main/java/evaluatorSpringBoot/repository/user_source_code0.rb";
  //private final String user_souce_code = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/src/main/java/evaluatorSpringBoot/repository/user_source_code0.rb";
  
  @PostMapping(value = "/cheers", headers="Accept=application/json", consumes = "application/JSON")
  public Code postEvaluate(@RequestBody String name) throws IOException, Exception {
	 JSONObject obj = new JSONObject(name);
	  
	 String code = obj.getString("code");
	  
	createTestFile(code);
	
    dockerClient = DockerClientBuilder.getInstance().build();
    
    Volume volume1 = new Volume("/app");

    CreateContainerResponse container = dockerClient.createContainerCmd("e93b746e985b")
      .withVolumes(volume1)
      .withBinds(new Bind(volume_path, volume1))
      .withCmd("ruby",test_file_path)
      .exec();
    
    System.out.println(container.getId());
   
    System.out.println("==========================");
    System.out.println(dockerClient.logContainerCmd(container.getId()));
    
    dockerClient.startContainerCmd(container.getId())
      .exec();
    
    FrameReaderITestCallback collectFramesCallback = new FrameReaderITestCallback();
    
     dockerClient.logContainerCmd(container.getId())
    	    .withFollowStream(true)
    		.withStdOut(true)
    		.withTailAll()
    		.exec(collectFramesCallback).awaitCompletion();
    
    System.out.println(collectFramesCallback.frames);
    
    String result = buildResponse();
    
    Code newCode =  new Code(counter.incrementAndGet(),
                         result);
    
    System.out.println(newCode.code);
    //System.out.println(logs);
    return newCode;
  }
  
  public void createTestFile(String code) throws IOException{
	  //https://www.journaldev.com/825/java-create-new-file
      File new_file = new File(user_souce_code);

      String fileData = "def test;" + code + ";"+ "end";
      FileOutputStream fos = new FileOutputStream(new_file);
      fos.write(fileData.getBytes());
      fos.flush();
      fos.close();
      System.out.println("start122346576879");
      System.out.println(code);
  }


	public 	String buildResponse() throws IOException{
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
	
	public static class FrameReaderITestCallback extends LogContainerResultCallback {

        public List<Frame> frames = new ArrayList<>();

        @Override
        public void onNext(Frame item) {
            frames.add(item);
            super.onNext(item);
        }

    }
}

