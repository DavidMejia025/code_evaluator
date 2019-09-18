package evaluatorSpringBoot.services;

import java.io.IOException;

import evaluatorSpringBoot.poo.Response;

public interface CodeEvaluator {
	Response runEval() throws IOException;
}