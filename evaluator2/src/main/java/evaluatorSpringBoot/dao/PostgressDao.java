package evaluatorSpringBoot.dao;

import evaluatorSpringBoot.poos.Submission;

class PostgressDao implements SubmissionDao{

   public double sum(int number){
     return number + 4;
   }

   public void create(Submission wp){
      //DataPoll conn = new DataPool();
      //conn.getConnection()

      //Create SQL
      //String query = "INSERT INTO web_page VALUES(wp.html_parsed
      System.out.println("hi");
   }

}