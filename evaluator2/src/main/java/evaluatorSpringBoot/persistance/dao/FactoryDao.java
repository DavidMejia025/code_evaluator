package evaluatorSpringBoot.persistance.dao;

public class FactoryDao {
  //Factory should addapt depending on environments isnt it?
  public static SubmissionDao createSubmission() {
    return new SubmissionPostgresDaoImpl();
  }
  
  public static ResponseDao createResponse() {
    return new ResponsePostgresDaoImpl();
  }
}

