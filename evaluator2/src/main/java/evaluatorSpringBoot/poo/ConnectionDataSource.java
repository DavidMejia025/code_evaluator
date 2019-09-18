package evaluatorSpringBoot.poo;

import java.sql.Connection;

public class ConnectionDataSource {
  private int        conectionId;
  private Connection conection;
  private boolean    available ;

  public ConnectionDataSource(int conectionId, Connection conection, Boolean available) { 
      this.conectionId = conectionId;
      this.conection   = conection;
      this.available   = available;
  }

  public int getConnectionId() {
      return conectionId;
  }

  public Connection getConnection() {
      return conection;
  }
  
  public Boolean availability(){
    return available;
  }
  
  public void setAvailability(boolean value){
    this.available = value;
  }
}
