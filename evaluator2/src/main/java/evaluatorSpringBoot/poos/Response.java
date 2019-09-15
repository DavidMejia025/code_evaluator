package evaluatorSpringBoot.poos;

/*[recap this variable definitions]
final means that the class can not be inherit and varible can no change value*/

public class Response {
//[] private but with a getter/. This declaration corresponds to the setter stuff?

    //private final long     userId;
    private final long     submissionId;
    public  final String   output;
    public  final String   code;
    //private final long     userId = 123456;
    //private final String[] status = new String[3];
    //private final String   language = "ruby";
    private final int      exitCode = 200;

    public Response(long submissionId, String code, String output) {
        this.submissionId = submissionId;
        this.code = code;
        this.output = output;
    }

    public long getsubmissionId() {
        return submissionId;
    }

    public String getOutput() {
        return output;
    }

    public int getExitCode() {
        return exitCode;
    }
}
