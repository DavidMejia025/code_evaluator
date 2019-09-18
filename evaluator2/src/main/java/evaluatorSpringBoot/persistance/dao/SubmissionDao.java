package evaluatorSpringBoot.persistance.dao;

import java.util.Hashtable;
import java.util.List;

import evaluatorSpringBoot.poo.Submission;

public interface SubmissionDao{
  public void create(Submission newSubmission);
  
  public void update(Submission newSubmission);
  
  public List<Submission> getAll();
  
  public Submission find(int submissionId);  
  
  //could be a list of parameter so List<Hashtable>
  public Submission findBy(Hashtable<String, Integer>  parameter); 
  
  //where(name: 'Spartacus', rating: 4)
  
  //findOrCreate(name: 'Spartacus', rating: 4)
  
  //findOrCreateBy(name: 'Spartacus', rating: 4)
}