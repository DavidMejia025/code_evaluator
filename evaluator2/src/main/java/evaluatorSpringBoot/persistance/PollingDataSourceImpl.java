package evaluatorSpringBoot.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import evaluatorSpringBoot.persistance.poo.ConnectionDataSource;

//conection pool
public class PollingDataSourceImpl implements PoollingDataSource{
  private static PollingDataSourceImpl       poolInstance;
  private int                                maxConnections;
  private Map<Integer, ConnectionDataSource> connections;
  
  public ConnectionDataSource poolConnection() {
    int connId = getFreeConnectionId();
    ConnectionDataSource connection = getConnection(connId);

    return connection;
  }
  
  public ConnectionDataSource leaveConnection(ConnectionDataSource connection) {  
    this.connections.get(connection.getConnectionId()).setAvailability(true);
    
    return null;
  }
  
  public static PollingDataSourceImpl getInstance() {
    if (poolInstance == null) {
      poolInstance = new PollingDataSourceImpl();
    } 
    
    return poolInstance;
  }
  
  private void createConnections() {
      for (int i = 1; i <= this.maxConnections; i++) {
        this.connections.put(i, createConnection(i));
      }
      
      System.out.println("Connections were created successfully!");
  }
  
  private ConnectionDataSource createConnection(int connId) {
    Connection conn = null;
    
    try {
    conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/code_eval_db",
        "postgres",
        "123pormi"
      );
    
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    
    return new ConnectionDataSource(connId, conn, true);
  }

  public int getFreeConnectionId() {
    int conId = 0;
    
    for (int i = 1; i <= this.maxConnections; i++) {
      if (this.connections.get(i).availability() == true) {
        conId = i; 
        i = this.maxConnections + 1;
      }
    }
    
    return conId;
  }
  
  public ConnectionDataSource getConnection(int connId) {
    ConnectionDataSource connection =  this.connections.get(connId);
    connection.setAvailability(false);
    
    return connection;
  }
  
  private PollingDataSourceImpl() {
    this.maxConnections = 5;
    this.connections    = new HashMap<Integer, ConnectionDataSource>();
    
    createConnections();
  }
}
