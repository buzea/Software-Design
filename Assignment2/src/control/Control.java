package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.AdminWindow;
import view.EmployeeWindow;
import view.LoginWindow;

public class Control {
	
	private LoginWindow loginWindow;
	//private AdminWindow adminWindow;
	//private EmployeeWindow employeeWindow;
	
	public Control() {
		loginWindow = new LoginWindow(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pass = new String(loginWindow.getPasswordField().getPassword());
				String user = loginWindow.getUsernameField().getText();
				if(user.equals("employee"))
					EmployeeWindow.main(null);
				if(user.equals("admin")&&pass.equals("root"))
					AdminWindow.main(null);
				
				loginWindow.close();
				
			}
		});
		
		
	}
	
	public static void main(String[] args){
		Control control = new Control();
		control.loginWindow.start();
		
	}

}
