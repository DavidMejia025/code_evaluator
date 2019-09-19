package evaluatorSpringBoot.core.poo;

/*[recap this variable definitions]
final means that the class can not be inherit and varible can no change value*/

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
    //private final String language = "ruby";
    private Response result; 
    private int      exitCode = 200;   

    public Submission(String code) {  
        this.submissionId = createSubmissionId(code);
        this.code = code;
        this.result = new Response(1,code, "");
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

    public int getExitCode() {
        return exitCode;
    }
    
    private int createSubmissionId(String code) {
    	return 2;
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
  
    public void setExitCode(int code) {
      this.exitCode = code;
    }
}
