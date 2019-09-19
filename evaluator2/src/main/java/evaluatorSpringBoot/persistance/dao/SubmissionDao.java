package evaluatorSpringBoot.persistance.dao;

import java.util.Hashtable;
import java.util.List;

import evaluatorSpringBoot.core.poo.Submission;

public interface SubmissionDao{
  public void create(Submission newSubmission);
  
  public void update(Submission newSubmission);
  
  public List<Submission> getAll();
  
  public Submission find(int submissionId);  
  
  public Submission findBy(Hashtable<String, Integer>  parameter); 
}
