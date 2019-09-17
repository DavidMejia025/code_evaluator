package evaluatorSpringBoot.poos;

import java.sql.Connection;

public class ConnectionDataSource {
  private int        connectionId;
  private Connection conn;;

  public ConnectionDataSource(int connectionId, Connection conn) { 
      this.connectionId = connectionId;
      this.conn         = conn;
  }

  public int connectionId() {
      return connectionId;
  }

  public Connection getConn() {
      return conn;
  }
}
