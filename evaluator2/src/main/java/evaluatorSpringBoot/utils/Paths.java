package evaluatorSpringBoot.utils;

public class Paths {
  public static String CurrDirectory() {
    String path = System.getProperty("user.dir");
    
    System.out.println("Working Directory = " + path);
    return path;
  }
}
