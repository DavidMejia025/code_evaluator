package evaluatorSpringBoot.persistance;

import evaluatorSpringBoot.poo.ConnectionDataSource;

public interface PoollingDataSource {
  public ConnectionDataSource poolConnection();
  
  public ConnectionDataSource closeConnection(ConnectionDataSource connection);
}
