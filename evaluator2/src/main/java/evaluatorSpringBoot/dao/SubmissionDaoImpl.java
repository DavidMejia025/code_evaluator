package evaluatorSpringBoot.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import evaluatorSpringBoot.poos.Submission;

public class SubmissionDaoImpl implements SubmissionDao{
  Connection connected;
  
	public SubmissionDaoImpl(){
	  this.connected = connect();
	}
	
	public Connection connect() {
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(
            "jdbc:postgresql://127.0.0.1:5432/test_java", "postgres", "123pormi");
        
        System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return conn;
	}
	
	public List<Submission> sql(String sql) {
	  Submission newSubmission = new Submission("");
	  List<Submission> submissions = new ArrayList<Submission>();
	  
	  try  {
      PreparedStatement pstmt = this.connected.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      
      while (rs.next()) {
        newSubmission.setSubmissionId(Integer.parseInt(rs.getString(1)));
        newSubmission.setUserId(Integer.parseInt(rs.getString(2)));
        newSubmission.setCode(rs.getString(4));
        newSubmission.setExitCode(Integer.parseInt(rs.getString(5)));
        
        submissions.add(newSubmission);
      }


     // this.connected.close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } 
	  
    return submissions;
	}
	
	public void create(Submission newSubmission) {
    String insertSubmission = "INSERT INTO submissions (submissionid, userid, status, code, exitcode)" +
                                              " VALUES (" + 
                                                  newSubmission.getSubmissionId() + ", " +
                                                  newSubmission.getUserId() + ", " +
                                                  "'started'" + ", " +
                                                  newSubmission.getCode() +", "+
                                                  newSubmission.getExitCode() +
                                                ");";
    System.out.println(insertSubmission);
    
    try  {
	    this.connected.createStatement().executeUpdate(insertSubmission);
	    System.out.println("Inserted data: " + insertSubmission);
	    
	   // this.connected.close();
  	} catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } 
	}
	
	public Submission find(int submissionId) {
	  Submission newSubmission = new Submission("");
    String findSubmission = "SELECT * FROM submissions WHERE submissionid = " + submissionId + ";";
    System.out.println(findSubmission);
    
    try  {
      PreparedStatement pstmt = this.connected.prepareStatement(findSubmission);
      ResultSet rs = pstmt.executeQuery();
      
      while (rs.next()) {
        newSubmission.setSubmissionId(Integer.parseInt(rs.getString(1)));
        newSubmission.setUserId(Integer.parseInt(rs.getString(2)));
        newSubmission.setCode(rs.getString(4));
        newSubmission.setExitCode(Integer.parseInt(rs.getString(5)));
      }
     
      System.out.println(newSubmission);

     // this.connected.close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } 
    return newSubmission;
  }
	
	public List<Submission> getAll() {
    Submission newSubmission = new Submission("");
    List<Submission> submissions = new ArrayList<Submission>();
    
    String findSubmission = "SELECT * FROM submissions;";
    
    try  {
      PreparedStatement pstmt = this.connected.prepareStatement(findSubmission);
      ResultSet rs = pstmt.executeQuery();
      
      while (rs.next()) {
        newSubmission.setSubmissionId(Integer.parseInt(rs.getString(1)));
        newSubmission.setUserId(Integer.parseInt(rs.getString(2)));
        newSubmission.setCode(rs.getString(4));
        newSubmission.setExitCode(Integer.parseInt(rs.getString(5)));
        
        submissions.add(newSubmission);
      }
     
      System.out.println(submissions);

     // this.connected.close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } 
    return submissions;
  }
	
	public void update(Submission newSubmission) {
    String updateSubmission = "UPDATE submissions SET " +
                                                  "submissionid = " + newSubmission.getSubmissionId() + ", " +
                                                  "userid = " + newSubmission.getUserId() + ", " +
                                                  "status = " + "'started'" + ", " +
                                                  "code = '" + newSubmission.getCode() +"', "+
                                                  "exitcode = " + newSubmission.getExitCode() + " " +
                                                  "WHERE submissionid = " + newSubmission.getSubmissionId() +
                                                ";";
    System.out.println(updateSubmission);
    
    try  {
      this.connected.createStatement().executeUpdate(updateSubmission);
      System.out.println("Inserted data: " + updateSubmission);
      
     // this.connected.close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } 
  }
	
	public Submission findBy(Hashtable<String, Integer> parameters) {
    Submission newSubmission = new Submission("");
    
    String key = parameters.keys().nextElement();
    int    val = parameters.get(key);
    
    String findSubmission = "SELECT * FROM submissions WHERE " + key + " = " + val + ";";
    System.out.println(findSubmission);
    
    try  {
      PreparedStatement pstmt = this.connected.prepareStatement(findSubmission);
      ResultSet rs = pstmt.executeQuery();
      
      while (rs.next()) {
        newSubmission.setSubmissionId(Integer.parseInt(rs.getString(1)));
        newSubmission.setUserId(Integer.parseInt(rs.getString(2)));
        newSubmission.setCode(rs.getString(4));
        newSubmission.setExitCode(Integer.parseInt(rs.getString(5)));
      }
     
      System.out.println(newSubmission);

     // this.connected.close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } 
    return newSubmission;
  }
}
