package evaluatorSpringBoot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import evaluatorSpringBoot.persistance.dao.ResponseDao;
import evaluatorSpringBoot.persistance.dao.ResponsePostgresDaoImpl;
import evaluatorSpringBoot.persistance.dao.SubmissionDao;
import evaluatorSpringBoot.persistance.dao.SubmissionPostgresDaoImpl;
import evaluatorSpringBoot.services.LogSystem;
import evaluatorSpringBoot.services.LogSystemImpl;

@Configuration
public class Config {
  @Bean
  public SubmissionDao submissionDao() {
    return new SubmissionPostgresDaoImpl();
  }

  @Bean
  public ResponseDao responseDao() {
    return new ResponsePostgresDaoImpl();
  }
  
  @Bean
  public LogSystem Logs() {
    return new LogSystemImpl();
  }
}