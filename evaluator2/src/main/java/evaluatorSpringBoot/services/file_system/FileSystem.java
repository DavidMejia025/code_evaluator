package evaluatorSpringBoot.services.file_system;

public interface FileSystem {
	void createTestFile(String body);
	
	String readTestFile();
}