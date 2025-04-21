import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private boolean loggedIn = false;

    public LoginDialog() {
        setTitle("Login");
        setSize(1200, 800); // Adjusted size
        setModal(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setPreferredSize(new Dimension(400, 150));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> checkLogin());
        panel.add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> System.exit(0));
        panel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panel, gbc);
        setLocationRelativeTo(null);
    }

    private void checkLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if ("Admin".equals(username) && "CNIT25501".equals(password)) {
            loggedIn = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}