package evaluatorSpringBoot.dao;

import java.util.Hashtable;

import evaluatorSpringBoot.dao.SubmissionDaoImpl;
import evaluatorSpringBoot.poos.Submission;

public class DaoTest {

  public static void testSumbisionDao() {
    SubmissionDaoImpl sDAO =  new SubmissionDaoImpl();
    
    Submission newSub = sDAO.find(1);
     
    System.out.println(newSub.getSubmissionId()); 
    
    System.out.println(sDAO.getAll());
    
    newSub.setUserId(530);
    
    sDAO.update(newSub);
    
    newSub = sDAO.find(2);
    
    System.out.println(newSub.getUserId()); 
    
    Hashtable<String, Integer>  parameter = new Hashtable<String, Integer>();
    
    parameter.put("userId", 530); 
    
    newSub = sDAO.findBy(parameter);
    
    sDAO.update(newSub);
    
    System.out.println(newSub.getUserId()); 
    System.out.println("end");
    System.out.println(sDAO.sql("SELECT * FROM submissions WHERE userid = 2"));
  }
}
