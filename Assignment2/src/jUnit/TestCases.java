package jUnit;

import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import model.library.Book;
import model.library.Library;
import model.users.Admin;
import model.users.Employee;
import model.users.User;

import org.junit.Test;

public class TestCases {


	@Test
	public void testUser() {
		if(User.login("Vlad", "1234")==null)
			fail("User not found");
		

		
	}
	
	@Test
	public void testLibrary(){
		//Admin admin = new Admin("", "");
		//Book hobbit = new Book("The Hobbit", "J. R. R. Tolkien", "Fiction", 1937, 200, 45.98);
		
		Library library =  Library.getInstance();
		//System.out.println(library);

		Employee raul = new Employee("Raul", "12345");
		Admin admin = new Admin("","");
		//if(!admin.createEmployee(raul))
		
		Class<Library> aBook = Library.class;
		for(Method m:aBook.getMethods()){
		//	System.out.println(m.getName());
		}
		
		Constructor<Library>[] libConstruct = (Constructor<Library>[]) aBook.getConstructors();
		for(Constructor<Library> c: libConstruct){
			System.out.println(c.getName());
		}
		

	}
	
	

}
