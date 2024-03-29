package evaluatorSpringBoot.core.poo;

import java.util.Random;

public class Submission {
	enum Statuses {
 	   received,
 	   queue,
 	   in_progress,
 	   failed,
 	   completed
    }
	
    private int      submissionId;
    private int      userId = 2;
    private Statuses status;
    public  String   code;
    public  String   stdoutPath = "";
    private int      resultId = 1;   

    public Submission(String code) {  
      this.submissionId = createSubmissionId(code);
      this.code = code;
      this.status = Statuses.received;
    }

    public int getSubmissionId() {
      return submissionId;
    }
    
    public int getUserId() {
      return userId;
    }

    public String getCode() {
      return code;
    }
    
    public Statuses getStatus() {
      return status;
    }

    public int getResultId() {
      return resultId;
    }
    
    private int createSubmissionId(String code) {  
      Random rand = new Random();
      int id = rand.nextInt(200);
      
      return this.submissionId = id;
    }
    
    public 	String getStdoutPath() {
      return stdoutPath;
    }
    
    public void setStdoutPath(String stdoutPath) {
    	this.stdoutPath = stdoutPath;
    }
    
    public void setSubmissionId(int id) {
      this.submissionId = id;
    }
    
    public void setUserId(int id) {
      this.userId = id;
    }
  
    public void setCode(String code) {
      this.code = code;
    }
    
    public void setStatus(Statuses status) {
      this.status = status;
    }
  
    public void setResultId(int code) {
      this.resultId = resultId;
    }
}
