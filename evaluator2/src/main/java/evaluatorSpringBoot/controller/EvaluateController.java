  package evaluatorSpringBoot.controller;
/* Http request:
 * params = {code: "puts 'Hello world from Docker Yeah!!!'"}.to_json
 * headers = {"Content-Type"=>"application/json"}
 * response = HTTParty.post('http://localhost:8080/cheers', body: params, headers: headers)
 */

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import evaluatorSpringBoot.core.CodeEvaluatorImpl;
import evaluatorSpringBoot.core.poo.Response;


@RestController
public class EvaluateController {
  @PostMapping(value = "/v1", headers="Accept=application/json", consumes = "application/JSON")
  public Response postEvaluate(@RequestBody String params) throws IOException, Exception {
	  Response newCodeEvaluator =  new CodeEvaluatorImpl(params).runEval();
	
	  return newCodeEvaluator;
  }
}
  