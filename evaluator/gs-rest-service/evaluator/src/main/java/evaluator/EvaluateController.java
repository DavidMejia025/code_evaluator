package evaluator;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//[study annotations]
@RestController

public class EvaluateController {
  private static final String template = "Hello,%s";
//[investigate AtomicLong type obj]
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping("/greeting")
  public Code greeting(@RequestParam(value="name", defaultValue="World") String name) {
     return new Code(counter.incrementAndGet(),
                         String.format(template,name));
  }
}
