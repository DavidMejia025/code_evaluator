package evaluatorSpringBoot.persistance.dao;

import evaluatorSpringBoot.persistance.ConnectionsDataSource;
import evaluatorSpringBoot.poo.Submission;

public class DaoTest {

  /*public class SubmissionDaoImpl {
    new FactoryJDBC
  }
  public class FactoryPersistance {
    this.dao = newSubmisionPostgresImpl
        newSubmisionMysqlImpl
        newMongo
        newMemory
  }*/
  
  public static void testSumbisionDao() {
    ConnectionsDataSource.createConnectionsDataSource();
    
    SubmissionDao sDAO =  SubmissionFactoryDao.create();
    
    SubmissionDao sDAO2 =  SubmissionFactoryDao.create();
    
    System.out.println(sDAO.getAll());  
    
    Submission newSub = sDAO.find(1);
    
    System.out.println(sDAO2.getAll());
    
    newSub = sDAO2.find(1);
    //SubmissionDao sDAO =  SubmissionDaoFactory.create();// sinleton 
    
    newSub = sDAO.find(1);
    
    SubmissionDao sDAO3 =  SubmissionFactoryDao.create();
    
    System.out.println(sDAO3.getAll());
     
     newSub = sDAO3.find(1);
     
    //System.out.println(newSub.getSubmissionId()); 
    
    System.out.println(sDAO3.getAll());
    
   /* newSub.setUserId(530);
    
    sDAO.update(newSub);
    
    newSub = sDAO.find(2);
    
    System.out.println(newSub.getUserId()); 
    
    Hashtable<String, Integer>  parameter = new Hashtable<String, Integer>();
    
    parameter.put("userId", 530); 
    
    newSub = sDAO.findBy(parameter);
    
    sDAO.update(newSub);
    
    System.out.println(newSub.getUserId()); 
    System.out.println("end");
    System.out.println(sDAO.sql("SELECT * FROM submissions WHERE userid = 2"));*/
  }
}
