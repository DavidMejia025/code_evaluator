package evaluatorSpringBoot.persistance;

import evaluatorSpringBoot.persistance.poo.ConnectionDataSource;

public interface PoollingDataSource {  
  public ConnectionDataSource poolConnection();
  
  public ConnectionDataSource leaveConnection(ConnectionDataSource connection);
  
 //I canot reference here the static method
 //public  PollingDataSourceImpl getInstance();
}
