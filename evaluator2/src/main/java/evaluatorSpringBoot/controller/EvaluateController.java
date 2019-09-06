package evaluatorSpringBoot.controller;


import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientBuilder;

import evaluatorSpringBoot.Code;

//[study annotations]
@RestController
public class EvaluateController {
  //public static final RequestMethod POST;
  private static final String template = "Hello,%s";
  private final AtomicLong counter = new AtomicLong();
  private final String directory = "docker";
  private DockerClient dockerClient;

  @PostMapping(value = "/cheers", headers="Accept=application/json", consumes = "application/JSON")
  public Code postEvaluate(@RequestBody String name) {
    dockerClient = DockerClientBuilder.getInstance().build();
    List<Container> containers = dockerClient.listContainersCmd().exec();
    System.out.println(containers.toString());
   // dockerClient.buildImageCmd(new File("src/test/resources", directory)).withNoCache(true)
    //.exec(new BuildImageResultCallback()).awaitImageId();
    Info info = dockerClient.infoCmd().exec();
    System.out.print(info); 
    
    System.out.println(name.getClass().getName());
    System.out.println(name);
    Code newCode =  new Code(counter.incrementAndGet(),
                         String.format(template,name));

    System.out.println(newCode.code);
      return newCode;
  }
}
