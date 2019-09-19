package evaluatorSpringBoot.persistance.dao;

import evaluatorSpringBoot.core.poo.Submission;
public class DaoTest {
  public static void testSumbisionDao() {
    SubmissionDao sDAO =  SubmissionFactoryDao.create();
    
    Submission newSub = sDAO.find(2);

    System.out.println(newSub.getUserId()); 
  }
}

