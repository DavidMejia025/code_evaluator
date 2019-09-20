package evaluatorSpringBoot.persistance.dao;

import java.util.Hashtable;
import java.util.List;

import evaluatorSpringBoot.core.poo.Response;

public interface ResponseDao {
  public void create(Response newResponse);
  
  public void update(Response newResponse);
  
  public List<Response> getAll();
  
  public Response find(int ResponseId);  
  
  public Response findBy(Hashtable<String, Integer>  parameter); 
}
