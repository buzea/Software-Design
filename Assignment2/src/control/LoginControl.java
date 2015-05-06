package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.users.Admin;
import model.users.User;
import view.LoginWindow;

public class LoginControl {

	private LoginWindow loginWindow;

	public LoginControl() {
		loginWindow = new LoginWindow(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = new String(loginWindow.getPasswordField().getPassword());
				String username = loginWindow.getUsernameField().getText();
				User user = User.login(username, password);
				if (user != null) {
					if (user.getType()==User.Type.ADMIN)
						new AdminControl((Admin)user);
					else
						new EmployeeControl();

				}else{
					JOptionPane.showMessageDialog(loginWindow.getFrmLibraryLogin(), "Invalid Login Credentials!", "Autentification ERROR", 0);
				}
				

			}
		});

	}

	public static void main(String[] args) {
		LoginControl control = new LoginControl();
		control.loginWindow.start();

	}

}
