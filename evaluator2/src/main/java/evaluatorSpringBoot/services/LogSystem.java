package evaluatorSpringBoot.services;

import com.github.dockerjava.api.DockerClient;
import evaluatorSpringBoot.core.poo.Submission;

public interface LogSystem {
  void addLog(String log);
  
  //void storeLog(String log);
}