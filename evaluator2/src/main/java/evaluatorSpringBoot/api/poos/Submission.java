package evaluatorSpringBoot.api.poos;

/*[recap this variable definitions]
final means that the class can not be inherit and varible can no change value*/

public class Submission {
//[] private but with a getter/. This declaration corresponds to the setter stuff?

    private final long     submissionId;
    //private final long     userId = 123456;
    //private final String[] status = new String[3];
    public  final String   code;
    //private final String language = "ruby";
    private final Response result; 
    private final int      exitCode = 200;

    public Submission(long id, String code) {
        this.submissionId = createSubmissionId(code);
        this.code = code;
        this.result = new Response(1,code, "");
    }

    public long getSubmissionId() {
        return submissionId;
    }

    public String getCode() {
        return code;
    }

    public int getExitCode() {
        return exitCode;
    }
    
    private int createSubmissionId(String code) {
    	return 100;
    }
}
