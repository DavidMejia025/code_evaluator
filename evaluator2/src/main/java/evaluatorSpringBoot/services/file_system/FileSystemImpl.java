package evaluatorSpringBoot.services.file_system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileSystemImpl implements FileSystem {
	private final String result_path     = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/src/main/java/evaluatorSpringBoot/repository/result.txt";
    private final String user_souce_code = "/home/deif/Dropbox/Elite/projects/code_evaluator/evaluator2/src/main/java/evaluatorSpringBoot/repository/user_source_code.rb";
	
    @Override
// It is not possible to create a static method when it comes from an interface?
	public void createTestFile(String body) {
		File new_file = new File(user_souce_code);
	    String fileData = "def test;" + body + ";"+ "end2";
	    
	    generateFOS(new_file, fileData);
	    System.out.println(body);
    }
	
	public String readTestFile(){
		String result = "";
	    String line = null;
	    
		try {
	    	FileReader fileReader = new FileReader(result_path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
	      
	        while((line = bufferedReader.readLine()) != null) {
	            System.out.println(line);
	            result += "-----" + line + "-----";
	        }  
	        
	        bufferedReader.close();  	      
	    }
	    catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + result_path + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + result_path + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
		
		return result;
	}
	
	private void generateFOS(File new_file, String fileData) {
		try {
			FileOutputStream fos = new FileOutputStream(new_file);
			
			fos.write(fileData.getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}