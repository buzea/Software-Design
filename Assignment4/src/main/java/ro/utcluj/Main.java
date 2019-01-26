package ro.utcluj;

import ro.utcluj.frames.ApplicationWindow;

import java.awt.*;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ApplicationWindow window = new ApplicationWindow();
				window.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
