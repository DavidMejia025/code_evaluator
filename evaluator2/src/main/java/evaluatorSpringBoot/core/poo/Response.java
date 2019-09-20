package evaluatorSpringBoot.core.poo;

import java.util.Random;

public class Response {
    private  int     responseId;
    private  int     submissionId;
    public   String  output;
    private  int     userId;
    private  int     exitCode;

    public Response(int submissionId, String output, int exitCode ) {
      this.responseId    = createResponseId();
      this.submissionId  = submissionId;
      this.output        = output;
      this.exitCode      = exitCode; 
    }

    public int getResponseId() {
      return responseId;
    }
    
    public int getSubmissionId() {
      return submissionId;
    }
    
    public int getUserId() {
      return userId;
    }
    
    public String getOutput() {
      return output;
    }

    public int getExitCode() {
      return exitCode;
    }
    
    public void setResponseId(int id) {
      this.responseId = id;
    }
    
    public void setSubmissionId(int submissionId) {
       this.submissionId = submissionId;
    }
    
    public void setUserId(int userId) {
      this.userId = userId;
    }
    
    public void setOutput(String output) {
      this.output = output ;
    }

    public void setExitCode(int exitCode) {
      this.exitCode = exitCode;
    }
    
    private int createResponseId() {
      Random rand = new Random();
      int id = rand.nextInt(200);
      
      return id;
    }
}
