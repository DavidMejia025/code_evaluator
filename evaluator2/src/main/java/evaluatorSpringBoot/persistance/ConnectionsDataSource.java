package evaluatorSpringBoot.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import evaluatorSpringBoot.poo.ConnectionDataSource;

public class ConnectionsDataSource {
//For now until DDL maxCOnnections will be burned.
  private static int          maxConnections = 5;
  public  static Map<Integer, ConnectionDataSource> connections = new HashMap<Integer, ConnectionDataSource>();

  public static void createConnectionsDataSource() {
    //There should be a config method to set the port db user id max capacity etc (or with env variables)
      for (int i = 1; i <= maxConnections; i++) {
        connections.put(i, createConnection(i));
      }
      
      System.out.println("#" +  maxConnections + " Connections were created");
  }
  
  public static void closeConnectionsDataSource() {
    
  }
  
  private static ConnectionDataSource createConnection(int connId) {
    Connection conn = null;
    
    try {
    conn = DriverManager.getConnection(
        "jdbc:postgresql://127.0.0.1:5432/test_java", "postgres", "123pormi");
    
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    
    return new ConnectionDataSource(connId, conn, true);
  }

  public static int getFreeConnectionId() {
    int conId = 0;
    
    for (int i = 1; i <= maxConnections; i++) {
      if (connections.get(i).availability() == true) {
        conId = i; 
        i = maxConnections + 1;
      }
    }
    
    System.out.println("Connection " + conId + "  Is available");
    return conId;
  }
  
  public static ConnectionDataSource getConnection(int connId) {
    //verify if this is a new object or if it is the object stored in connections
    ConnectionDataSource connection =  connections.get(connId);
    
    connection.setAvailability(false);
    
    return connection;
  }
  
  public static void closeConnection( ConnectionDataSource connection) {
    connections.get(connection.getConnectionId()).setAvailability(true);
  }
}
