package ro.utcluj.view;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import java.awt.*;

public class LoginWindow {

    private JFrame         frmLibraryLogin;
    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JButton        btnLogin;

    /**
     * Create the application.
     */
    public LoginWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmLibraryLogin = new JFrame();

        frmLibraryLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
        frmLibraryLogin.setTitle("Library Login");
        frmLibraryLogin.setBounds(100, 100, 450, 300);
        frmLibraryLogin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frmLibraryLogin.getContentPane().setLayout(
                new FlowLayout(FlowLayout.CENTER, 5, 5));

        JPanel panel = new JPanel();
        frmLibraryLogin.getContentPane().add(panel);
        panel.setLayout(new FormLayout(new ColumnSpec[]{
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),}, new RowSpec[]{
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

        JLabel lblUsername = new JLabel("Username :");
        panel.add(lblUsername, "4, 8");

        usernameField = new JTextField();

        usernameField.setText("admin");

        panel.add(usernameField, "8, 8, fill, default");
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Password: ");
        panel.add(lblPassword, "4, 10");

        passwordField = new JPasswordField();

        passwordField.setText("root");
        panel.add(passwordField, "8, 10, fill, default");

        btnLogin = new JButton("Login");

        panel.add(btnLogin, "4, 12");
    }

    /**
     * Launch the application.
     */
    public void start() {
        frmLibraryLogin.setVisible(true);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JFrame getFrmLibraryLogin() {
        return frmLibraryLogin;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }
}
