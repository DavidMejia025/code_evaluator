package evaluatorSpringBoot.services.file_system1;

public interface FileSystem {
	void createTestFile(String body);
	
	void createResultFile(String body);
	
	String readTestFile();
}