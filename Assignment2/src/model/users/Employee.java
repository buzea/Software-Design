package model.users;

import java.util.List;

import model.library.Book;

public class Employee extends User {

	public Employee(String username, String password) {
		super(username, password,User.Type.EMPLOYEE);
		
	}
	
	public List<Book> searchByTitle(String title){
		
		return null;
	}
	
	public List<Book> searchByAuthor(String author){
		
		return null;
	}
	
	public List<Book> searchByYear(int year){
		
		return null;
	}
	
	public void sell(Book book){
		
	}

}
