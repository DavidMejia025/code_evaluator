package evaluatorSpringBoot.persistance.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import evaluatorSpringBoot.persistance.PollingDataSourceImpl;
import evaluatorSpringBoot.poo.ConnectionDataSource;
import evaluatorSpringBoot.poo.Submission;

//Should this inherit from polling 
public class SubmissionPostgresDaoImpl implements SubmissionDao{
  public  ConnectionDataSource  connection;
  private PollingDataSourceImpl poollconnection;
	
  public SubmissionPostgresDaoImpl(){
    this.poollconnection = new PollingDataSourceImpl();
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
      this.connection = this.poollconnection.poolConnection();;
	    this.connection.getConnection().createStatement().executeUpdate(insertSubmission);
	    System.out.println("Inserted data: " + insertSubmission);
	    
	   // this.connection.getConnection().close();
  	} catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } 
    
    this.connection = this.poollconnection.closeConnection(this.connection);
	}
	
	public Submission find(int submissionId) {
	  Submission newSubmission = new Submission("");
    String findSubmission = "SELECT * FROM submissions WHERE submissionid = " + submissionId + ";";
    System.out.println(findSubmission);
    
    try  {
      this.connection = this.poollconnection.poolConnection();;
      PreparedStatement pstmt = this.connection.getConnection().prepareStatement(findSubmission);
      ResultSet rs = pstmt.executeQuery();
      
      while (rs.next()) {
        newSubmission.setSubmissionId(Integer.parseInt(rs.getString(1)));
        newSubmission.setUserId(Integer.parseInt(rs.getString(2)));
        newSubmission.setCode(rs.getString(4));
        newSubmission.setExitCode(Integer.parseInt(rs.getString(5)));
      }
     
      System.out.println(newSubmission);

     // this.connection.getConnection().close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } 
    
    this.connection = this.poollconnection.closeConnection(this.connection);
    
    return newSubmission;
  }
	
	public List<Submission> getAll() {
    Submission newSubmission = new Submission("");
    List<Submission> submissions = new ArrayList<Submission>();
    
    String findSubmission = "SELECT * FROM submissions;";
    
    try  {
      this.connection = this.poollconnection.poolConnection();;
      PreparedStatement pstmt = this.connection.getConnection().prepareStatement(findSubmission);
      ResultSet rs = pstmt.executeQuery();
      
      while (rs.next()) {
        newSubmission.setSubmissionId(Integer.parseInt(rs.getString(1)));
        newSubmission.setUserId(Integer.parseInt(rs.getString(2)));
        newSubmission.setCode(rs.getString(4));
        newSubmission.setExitCode(Integer.parseInt(rs.getString(5)));
        
        submissions.add(newSubmission);
      }
     
      System.out.println(submissions);

     // this.connection.getConnection().close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } 
    
    this.connection = this.poollconnection.closeConnection(this.connection);
    
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
      this.connection = this.poollconnection.poolConnection();;
      this.connection.getConnection().createStatement().executeUpdate(updateSubmission);
      System.out.println("Inserted data: " + updateSubmission);
      
     // this.connection.getConnection().close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } 
    
    this.connection = this.poollconnection.closeConnection(this.connection);
  }
	
	public Submission findBy(Hashtable<String, Integer> parameters) {
    Submission newSubmission = new Submission("");
    
    String key = parameters.keys().nextElement();
    int    val = parameters.get(key);
    
    String findSubmission = "SELECT * FROM submissions WHERE " + key + " = " + val + ";";
    System.out.println(findSubmission);
    
    try  {
      this.connection = this.poollconnection.poolConnection();
      PreparedStatement pstmt = this.connection.getConnection().prepareStatement(findSubmission);
      ResultSet rs = pstmt.executeQuery();
      
      while (rs.next()) {
        newSubmission.setSubmissionId(Integer.parseInt(rs.getString(1)));
        newSubmission.setUserId(Integer.parseInt(rs.getString(2)));
        newSubmission.setCode(rs.getString(4));
        newSubmission.setExitCode(Integer.parseInt(rs.getString(5)));
      }
     
      System.out.println(newSubmission);

     // this.connection.getConnection().close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } 
    
    this.connection = this.poollconnection.closeConnection(this.connection);
    
    return newSubmission;
  }
}
