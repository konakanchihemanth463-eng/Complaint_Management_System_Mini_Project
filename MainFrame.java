import javax.swing.*;
import java.awt.*;
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private final UserService userService = new UserService();
    private final AdminService adminService = new AdminService();
    private final ComplaintService complaintService = new ComplaintService();
    private UserPanel userPanel;
    public MainFrame() {
        setTitle("Complaint Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        JPanel loginPanel = new LoginPanel(this, userService, adminService);
        userPanel = new UserPanel(this, complaintService);
        JPanel adminPanel = new AdminPanel(this, complaintService);
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(userPanel, "USER_DASHBOARD");
        mainPanel.add(adminPanel, "ADMIN_DASHBOARD");
        add(mainPanel);
    }
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    public UserPanel getUserPanel() {
        return userPanel;
    }
}