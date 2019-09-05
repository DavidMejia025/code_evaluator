package evaluator;
/*[recap this variable definitions]
final means that the class can not be inherit and varible can no change value*/

public class Code {

    private final long id;
    private final String content;

    public Code(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
