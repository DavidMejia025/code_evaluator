package evaluatorSpringBoot.persistance.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import evaluatorSpringBoot.core.poo.Response;
import evaluatorSpringBoot.persistance.PollingDataSourceImpl;
import evaluatorSpringBoot.persistance.poo.ConnectionDataSource;

public class ResponsePostgresDaoImpl implements ResponseDao{
  private  ConnectionDataSource  connection;
  private  PollingDataSourceImpl poollconnection;
  
  public ResponsePostgresDaoImpl(){
    this.poollconnection = PollingDataSourceImpl.getInstance();
  }
  
  public void create(Response newResponse) {
    String insertResponse = "INSERT INTO Responses (Response_id, submission_id, user_id, output, exit_code)" +
      " VALUES (" + 
          newResponse.getResponseId() + ", " +
          newResponse.getSubmissionId() + ", " +
          newResponse.getUserId() + ", " +
          "'" + newResponse.getOutput() +"', " +
          newResponse.getExitCode() + ");"; 

    try  {
      this.connection = this.poollconnection.poolConnection();;
      this.connection.getConnection()
        .createStatement()
        .executeUpdate(insertResponse);
      
    } catch (Exception e) {
      System.err.println("Create new Response record Error: " + e.getMessage());
    }finally {  
      if (this.connection != null) {
        leaveConnection();
      }
    }
  }
  
  public Response find(int ResponseId) {
    Response newResponse = new Response(0,"", 0);
    String findResponse  = "SELECT * FROM Responses WHERE Responseid = " + ResponseId + ";";
    
    try  {
      this.connection         = this.poollconnection.poolConnection();
      PreparedStatement pstmt = this.connection.getConnection().prepareStatement(findResponse);
      ResultSet rs            = pstmt.executeQuery();
      
      while (rs.next()) {
        newResponse = createPOO(newResponse, rs);
      }

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } finally {  
      if (this.connection != null) {
        leaveConnection();
      }
    }
    
    return newResponse;
  }
  
  public List<Response> getAll() {
    Response newResponse     = new Response(0,"",0);
    List<Response> Responses = new ArrayList<Response>();
    
    String findResponse = "SELECT * FROM Responses;";
    
    try  {
      this.connection         = this.poollconnection.poolConnection();;
      PreparedStatement pstmt = this.connection.getConnection().prepareStatement(findResponse);
      ResultSet rs = pstmt.executeQuery();
      
      while (rs.next()) {
        newResponse = createPOO(newResponse, rs);
        
        Responses.add(newResponse);
      }

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } finally {  
      if (this.connection != null) leaveConnection();
    }
    
    return Responses;
  }
  
  public void update(Response newResponse) {
    String updateResponse = "UPDATE Responses SET " +
      "response_id        = " + newResponse.getResponseId() + ", " +
      "submission_id      = " + newResponse.getSubmissionId() + ", "+
      "user_id            = " + newResponse.getUserId() + ", " +
      "output             = " + newResponse.getOutput() + " " +
      "exit_code          = '" + newResponse.getExitCode()  +"', " +
      "WHERE Responseid  = " + newResponse.getResponseId() +";";
    
    try  {
      this.connection = this.poollconnection.poolConnection();
      this.connection.getConnection().createStatement().executeUpdate(updateResponse);
      
    } catch (Exception e) {
      System.err.println("Update Response  " + newResponse.getResponseId() + "  ErrorError: " + e.getMessage());
    } finally {  
      if (this.connection != null) {
        leaveConnection();
      }
    }
  }
  
  public Response findBy(Hashtable<String, Integer> parameters) {
    Response newResponse = new Response(0,"",0);
    
    String key = parameters.keys().nextElement();
    int    val = parameters.get(key);
    
    String findResponse = "SELECT * FROM Responses WHERE " + key + " = " + val + ";";
    
    try  {
      this.connection         = this.poollconnection.poolConnection();
      PreparedStatement pstmt = this.connection.getConnection().prepareStatement(findResponse);
      ResultSet rs            = pstmt.executeQuery();
      
      while (rs.next()) {
        newResponse = createPOO(newResponse, rs);
      }
      
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    } finally {  
      if (this.connection != null) {
        leaveConnection();
      }
    }
    
    return newResponse;
  }
  
  private Response createPOO(Response newResponse, ResultSet rs) {
    try {
      newResponse.setResponseId(Integer.parseInt(rs.getString(1)));
      newResponse.setSubmissionId(Integer.parseInt(rs.getString(2)));
      newResponse.setUserId(Integer.parseInt(rs.getString(2)));
      newResponse.setOutput(rs.getString(4));
      newResponse.setExitCode(Integer.parseInt(rs.getString(5)));
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
 
    return newResponse;
  }
  
  private void leaveConnection() {
    this.connection = this.poollconnection.leaveConnection(this.connection);
  }
}

