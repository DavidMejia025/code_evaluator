package evaluatorSpringBoot.persistance.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import evaluatorSpringBoot.core.poo.Submission;
import evaluatorSpringBoot.persistance.PollingDataSourceImpl;
import evaluatorSpringBoot.persistance.poo.ConnectionDataSource;

public class SubmissionPostgresDaoImpl implements SubmissionDao{
  private  ConnectionDataSource  connection;
  private  PollingDataSourceImpl poollconnection;
	
  public SubmissionPostgresDaoImpl(){
    this.poollconnection = PollingDataSourceImpl.getInstance();
  }
  
	public void create(Submission newSubmission) {
    String insertSubmission = "INSERT INTO submissions (submission_id, user_id, status, code, result_id)" +
      " VALUES (" + 
          newSubmission.getSubmissionId() + ", " +
          newSubmission.getUserId() + ", " +
          "'started'" + ", " +
          "'" + newSubmission.getCode() +"', " +
          newSubmission.getResultId() + ");"; 
    
    try  {
      this.connection = this.poollconnection.poolConnection();;
	    this.connection.getConnection()
        .createStatement()
        .executeUpdate(insertSubmission);
	    
  	} catch (Exception e) {
      System.err.println("Create new submission record Error: " + e.getMessage());
    }finally {  
      if (this.connection != null) {
        leaveConnection();
      }
    }
	}
	
	public Submission find(int submissionId) {
	  Submission newSubmission = new Submission("");
    String findSubmission    = "SELECT * FROM submissions WHERE submissionid = " + submissionId + ";";
    
    try  {
      this.connection         = this.poollconnection.poolConnection();
      PreparedStatement pstmt = this.connection.getConnection().prepareStatement(findSubmission);
      ResultSet rs            = pstmt.executeQuery();
      
      while (rs.next()) {
        newSubmission = createPOO(newSubmission, rs);
      }

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } finally {  
      if (this.connection != null) {
        leaveConnection();
      }
    }
    
    return newSubmission;
  }
	
	public List<Submission> getAll() {
    Submission newSubmission     = new Submission("");
    List<Submission> submissions = new ArrayList<Submission>();
    
    String findSubmission = "SELECT * FROM submissions;";
    
    try  {
      this.connection         = this.poollconnection.poolConnection();;
      PreparedStatement pstmt = this.connection.getConnection().prepareStatement(findSubmission);
      ResultSet rs = pstmt.executeQuery();
      
      while (rs.next()) {
        newSubmission = createPOO(newSubmission, rs);
        
        submissions.add(newSubmission);
      }

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } finally {  
      if (this.connection != null) leaveConnection();
    }
    
    return submissions;
  }
	
	public void update(Submission newSubmission) {
    String updateSubmission = "UPDATE submissions SET " +
      "submission_id       = " + newSubmission.getSubmissionId() + ", " +
      "user_id             = " + newSubmission.getUserId() + ", " +
      "status              = " + "'started'" + ", " +
      "code                = '" + newSubmission.getCode() +"', " +
      "result_id           = " + newSubmission.getResultId() + " " +
      "WHERE submissionid  = " + newSubmission.getSubmissionId() +";";
    
    try  {
      this.connection = this.poollconnection.poolConnection();
      this.connection.getConnection().createStatement().executeUpdate(updateSubmission);
      
    } catch (Exception e) {
      System.err.println("Update submission  " + newSubmission.getSubmissionId() + "  ErrorError: " + e.getMessage());
    } finally {  
      if (this.connection != null) {
        leaveConnection();
      }
    }
  }
	
	public Submission findBy(Hashtable<String, Integer> parameters) {
    Submission newSubmission = new Submission("");
    
    String key = parameters.keys().nextElement();
    int    val = parameters.get(key);
    
    String findSubmission = "SELECT * FROM submissions WHERE " + key + " = " + val + ";";
    
    try  {
      this.connection         = this.poollconnection.poolConnection();
      PreparedStatement pstmt = this.connection.getConnection().prepareStatement(findSubmission);
      ResultSet rs            = pstmt.executeQuery();
      
      while (rs.next()) {
        newSubmission = createPOO(newSubmission, rs);
      }
      
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } finally {  
      if (this.connection != null) {
        leaveConnection();
      }
    }
    
    return newSubmission;
  }
	
	private Submission createPOO(Submission newSubmission, ResultSet rs) {
	  try {
	    newSubmission.setSubmissionId(Integer.parseInt(rs.getString(1)));
	    newSubmission.setUserId(Integer.parseInt(rs.getString(2)));
	    newSubmission.setCode(rs.getString(4));
	    newSubmission.setResultId(Integer.parseInt(rs.getString(5)));
	  } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
	 
    return newSubmission;
	}
	
	private void leaveConnection() {
	  this.connection = this.poollconnection.leaveConnection(this.connection);
	}
}
