package model.report;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import model.library.Book;
import model.library.Library;

public class TextReport implements Report{
	
	private Library library;
	private static String formatStr="%-20s %-20s %-15s%n";

	public TextReport() {
		library= Library.getInstance();
	}

	@Override
	public boolean generateReport() {
		File file = new File("Report.txt");
		try{
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for(Book b:library.getBooks()){
			if(b.getQuantity()==0){
				bw.write(String.format(formatStr, b.getTitle(),b.getAuthor(),b.getYear()));
			}
		}
		bw.close();
		return true;
		}catch(Exception e){
			
		}
		return false;

	}

	@Override
	public void generateReport(String path) {
		// TODO Auto-generated method stub
		
	}

}
