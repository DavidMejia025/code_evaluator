package evaluatorSpringBoot.services;

import java.io.IOException;

import evaluatorSpringBoot.poos.Response;

public interface CodeEvaluator {
	Response runEval() throws IOException;
}