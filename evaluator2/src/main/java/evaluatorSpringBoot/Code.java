package evaluatorSpringBoot;

/*[recap this variable definitions]
final means that the class can not be inherit and varible can no change value*/

public class Code {
//[] private but with a getter/. This declaration corresponds to the setter stuff?

    private final long     codeId;
    //private final long     userId = 123456;
    //private final String[] status = new String[3];
    public  final String   code;
    //private final String   language = "ruby";
    //private final String   result = "done"; // result id?
    private final int      exitCode = 200;

    public Code(long id, String code) {
        this.codeId   = id;
        this.code     = code;
    }

    public long getCodeId() {
        return codeId;
    }

    public String getCode() {
        return code;
    }

    public int getExitCode() {
        return exitCode;
    }
}
