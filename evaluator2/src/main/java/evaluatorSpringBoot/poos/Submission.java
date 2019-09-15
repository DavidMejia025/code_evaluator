package evaluatorSpringBoot.poos;

/*[recap this variable definitions]
final means that the class can not be inherit and varible can no change value*/

public class Submission {
//[] private but with a getter/. This declaration corresponds to the setter stuff?
	enum Statuses {
 	   received,
 	   queue,
 	   in_progress,
 	   failed,
 	   completed
    }
	
    private final int     submissionId;
    private final int     userId = 0;
    private final Statuses status;
    public  final String   code;
    public  String         stdoutPath = "";
    //private final String language = "ruby";
    private final Response result; 
    private final int      exitCode = 200;  

    public Submission(String code) {  
        this.submissionId = createSubmissionId(code);
        this.code = code;
        this.result = new Response(1,code, "");
        this.status = Statuses.received;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public String getCode() {
        return code;
    }

    public int getExitCode() {
        return exitCode;
    }
    
    private int createSubmissionId(String code) {
    	return 1;
    }
    
    public 	String getStdoutPath() {
        return stdoutPath;
    }
    
    public void setStdoutPath(String stdoutPath) {
    	this.stdoutPath = stdoutPath;
    }
}
