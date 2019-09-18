package evaluatorSpringBoot.persistance;

import java.sql.Connection;

import evaluatorSpringBoot.persistance.ConnectionsDataSource;
import evaluatorSpringBoot.poo.ConnectionDataSource;

public class PollingDataSourceImpl implements PoollingDataSource{
  public ConnectionDataSource  poolConnection() {
    Connection conn1 = null;
    //find available connections
    int connId = ConnectionsDataSource.getFreeConnectionId();
    //get available connection
    ConnectionDataSource connection = ConnectionsDataSource.getConnection(connId);
    //return connection
    return connection;
  }
  
  public ConnectionDataSource closeConnection(ConnectionDataSource connection) {  
    ConnectionsDataSource.closeConnection(connection);
    
    return null;
  }
}
