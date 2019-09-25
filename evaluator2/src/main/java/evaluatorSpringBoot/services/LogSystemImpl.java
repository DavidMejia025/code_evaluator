package evaluatorSpringBoot.services;
import java.util.Arrays;
import java.util.Date;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class LogSystemImpl implements LogSystem {

  public void LogSystemImpl(){
    this.addLog("Log system start...");
  }

  public void addLog(String msj) {
    System.out.println(preMessage() + msj + postMessage());
  }
  
  private String preMessage() {
    Date dateobj = new Date();  
    
    return "[LOG] >>> " + "|" + dateobj +"| ";
  }
  
  private String postMessage() {
    String arr[] = new String[20];
    Arrays.fill(arr, ".");
    
    return " " + String.join("", arr);
  }
  
}