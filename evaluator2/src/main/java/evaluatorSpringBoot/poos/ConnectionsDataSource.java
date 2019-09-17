package evaluatorSpringBoot.poos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionsDataSource {
  public static Map<Integer, Map<Connection,Boolean>> connections = new HashMap<Integer, Map<Connection,Boolean>>();
  private static Map<Connection,Boolean> connection1 = new HashMap<Connection, Boolean>();
  private static Map<Connection,Boolean> connection2 = new HashMap<Connection, Boolean>();
  private static Map<Connection,Boolean> connection3 = new HashMap<Connection, Boolean>();
  private static Map<Connection,Boolean> connection4 = new HashMap<Connection, Boolean>();
  private static Map<Connection,Boolean> connection5 = new HashMap<Connection, Boolean>();

  public static void createConnectionsDataSource() {
      Connection conn1 = null;
      Connection conn2 = null;
      Connection conn3 = null;
      Connection conn4 = null;
      Connection conn5 = null;
      
      try {
        //this can be performed wrapped in one method and passing only the number
        //There should be a config method to set the port db user id max capacity etc (or with env variables)
          conn1 = DriverManager.getConnection(
              "jdbc:postgresql://127.0.0.1:5432/test_java", "postgres", "123pormi");
          connection1.put(conn1, true);
          
          conn2 = DriverManager.getConnection(
              "jdbc:postgresql://127.0.0.1:5432/test_java", "postgres", "123pormi");
          connection1.put(conn2, true);
          
          conn3 = DriverManager.getConnection(
              "jdbc:postgresql://127.0.0.1:5432/test_java", "postgres", "123pormi");
          connection1.put(conn3, true);
          
          conn4 = DriverManager.getConnection(
              "jdbc:postgresql://127.0.0.1:5432/test_java", "postgres", "123pormi");
          connection1.put(conn4, true);
          
          conn5 = DriverManager.getConnection(
              "jdbc:postgresql://127.0.0.1:5432/test_java", "postgres", "123pormi");
          connection1.put(conn5, true);
          
          connections.put(1, connection1);
          connections.put(2, connection2);
          connections.put(3, connection3);
          connections.put(4, connection4);
          connections.put(5, connection5);
          System.out.println("Connections created");
      } catch (SQLException e) {
          System.out.println(e.getMessage());
      }
  }

  public int getFreeConnectionId() {
      return 1;
  }
}
