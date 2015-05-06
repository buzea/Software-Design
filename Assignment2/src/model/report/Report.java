package model.report;

public interface Report {
	
	/**
	 * Generates report file in current project folder
	 * @return TODO
	 */
	public boolean generateReport();

	/**
	 * Generates report file at specified path
	 * @param path
	 */
	public void generateReport(String path);
	
}
