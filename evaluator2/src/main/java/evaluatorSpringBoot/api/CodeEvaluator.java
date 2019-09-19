package evaluatorSpringBoot.api;

import java.io.IOException;
import evaluatorSpringBoot.core.poo.Response;

public interface CodeEvaluator {
	Response runEval() throws IOException;
}