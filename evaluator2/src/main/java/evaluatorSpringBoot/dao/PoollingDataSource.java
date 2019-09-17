package evaluatorSpringBoot.dao;

import evaluatorSpringBoot.poos.ConnectionDataSource;

public interface PoollingDataSource {
  public ConnectionDataSource getConnection();
  
  public void closeConnection();
}
