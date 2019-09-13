package evaluatorSpringBoot.services.file_system;

public interface FileSystem {
	void createTestFile(String body);
	
	void createResultFile(String body);
	
	String readTestFile();
}