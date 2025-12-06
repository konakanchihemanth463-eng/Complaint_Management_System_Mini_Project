import javax.swing.*;
import java.awt.*;
public class LoginPanel extends JPanel {
    private MainFrame mainFrame;
    private UserService userService;
    private AdminService adminService;
    public LoginPanel(MainFrame mainFrame, UserService userService, AdminService adminService) {
        this.mainFrame = mainFrame;
        this.userService = userService;
        this.adminService = adminService;
        setLayout(new BorderLayout(10, 10));
        JLabel titleLabel = new JLabel("Complaint Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        JTextField usernameField = new JTextField(15);
        contentPanel.add(usernameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JPasswordField passwordField = new JPasswordField(15);
        contentPanel.add(passwordField, gbc);
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton adminLoginButton = new JButton("Admin Login");
        JButton exitButton = new JButton("Exit");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(adminLoginButton);
        buttonPanel.add(exitButton);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(buttonPanel, gbc);
        add(contentPanel, BorderLayout.CENTER);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                if (userService.login(username, password)) {
                    mainFrame.getUserPanel().setCurrentUser(username);
                    mainFrame.showPanel("USER_DASHBOARD");
                }
            } catch (UserNotFoundException | IncorrectPasswordException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
            } catch (FileAccessException ex) {
                JOptionPane.showMessageDialog(this, "Error accessing user data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty.", "Registration Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                if (userService.register(username, password)) {
                    JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Username already exists.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (FileAccessException ex) {
                JOptionPane.showMessageDialog(this, "Error during registration.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        adminLoginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                if (adminService.login(username, password)) {
                    mainFrame.showPanel("ADMIN_DASHBOARD");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Admin Credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
    }
}