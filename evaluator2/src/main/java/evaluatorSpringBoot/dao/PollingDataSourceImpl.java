package evaluatorSpringBoot.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import evaluatorSpringBoot.poos.ConnectionDataSource;

public class PollingDataSourceImpl implements PoollingDataSource{
  
  public ConnectionDataSource getConnection() {
    Connection conn1 = null;
    //find available connections
    //get available connection
    // create new conection 
    //return connection
    try {
       conn1 = DriverManager.getConnection(
        "jdbc:postgresql://127.0.0.1:5432/test_java", "postgres", "123pormi");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return new ConnectionDataSource(1, conn1);
  }
  
  public void closeConnection() {
    
  }
}
