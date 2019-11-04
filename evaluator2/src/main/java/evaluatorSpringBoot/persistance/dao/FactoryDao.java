package evaluatorSpringBoot.persistance.dao;

public class FactoryDao {
  public static SubmissionDao createSubmission() {
    return new SubmissionPostgresDaoImpl();
  }

  public static ResponseDao createResponse() {
    return new ResponsePostgresDaoImpl();
  }
}
