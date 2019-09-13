package evaluatorSpringBoot.api.controller;
/* Http request:
 * params = {code: "puts 'Hello world from Docker Yeah!!!'"}.to_json
 * headers = {"Content-Type"=>"application/json"}
 * response = HTTParty.post('http://localhost:8080/cheers', body: params, headers: headers)
 */

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import evaluatorSpringBoot.api.TestCodeImpl;
import evaluatorSpringBoot.api.poos.Code;
import evaluatorSpringBoot.api.poos.Response;

@RestController
public class EvaluateController {
  @PostMapping(value = "/cheers", headers="Accept=application/json", consumes = "application/JSON")
  public Code postEvaluate(@RequestBody String params) throws IOException, Exception {
	  Response newTestCode =  new TestCodeImpl(params).runEval();
   
	  Code newCode =  new Code(1,
              "hello world");

	  System.out.println(newCode.code);
	
	  return newCode;
  }
}
  