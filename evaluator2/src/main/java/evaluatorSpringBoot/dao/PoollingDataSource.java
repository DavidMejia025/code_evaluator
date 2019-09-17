package evaluatorSpringBoot.dao;

import java.util.List;

import evaluatorSpringBoot.poos.Submission;

public interface PoollingDataSource {
  public List<Submission> sql(String sql);
}
