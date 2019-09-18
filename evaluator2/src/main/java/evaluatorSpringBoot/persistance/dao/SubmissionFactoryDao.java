package evaluatorSpringBoot.persistance.dao;

public class SubmissionFactoryDao {
  
  //Factory should addapt depending on environments isnt it?
  public static SubmissionDao create() {
    return new SubmissionPostgresDaoImpl();
  }
}

