package ro.utcluj.control;

import ro.utcluj.model.users.Admin;
import ro.utcluj.model.users.User;
import ro.utcluj.view.LoginWindow;

import javax.swing.*;

public class LoginControl {

	private final LoginWindow loginWindow;

	public LoginControl() {
		loginWindow = new LoginWindow();
	}

	public void start() {
		bindAction();
		loginWindow.start();
	}

	private void bindAction() {
		loginWindow.getBtnLogin().addActionListener(e -> {
			String password = new String(loginWindow.getPasswordField().getPassword());
			String username = loginWindow.getUsernameField().getText();
			User user = User.login(username, password);
            if (user != null) {
				if (user.getType() == User.Type.ADMIN)
					new AdminControl((Admin) user);
				else
					new EmployeeControl();

			} else {
				JOptionPane.showMessageDialog(loginWindow.getFrmLibraryLogin(), "Invalid Login Credentials!", "Authentication ERROR", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
