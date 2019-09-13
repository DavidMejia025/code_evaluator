package evaluatorSpringBoot.api.controller;
/* Http request:
 * params = {code: "puts 'Hello world from Docker Yeah!!!'"}.to_json
 * headers = {"Content-Type"=>"application/json"}
 * response = HTTParty.post('http://localhost:8080/cheers', body: params, headers: headers)
 */

import java.io.IOException;

import org.aopalliance.reflect.Code;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import evaluatorSpringBoot.api.TestCodeImpl;
import evaluatorSpringBoot.api.poos.Response;
import evaluatorSpringBoot.api.poos.Submission;

@RestController
public class EvaluateController {
  @PostMapping(value = "/cheers", headers="Accept=application/json", consumes = "application/JSON")
  public Response postEvaluate(@RequestBody String params) throws IOException, Exception {
	  Response newTestCode =  new TestCodeImpl(params).runEval();
	
	  return newTestCode;
  }
}
  