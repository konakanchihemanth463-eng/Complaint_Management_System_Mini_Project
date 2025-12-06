import javax.swing.*;
import java.awt.*;
public class UserPanel extends JPanel {
    private MainFrame mainFrame;
    private ComplaintService complaintService;
    private String currentUser;
    private JTextArea complaintsArea;
    public UserPanel(MainFrame mainFrame, ComplaintService complaintService) {
        this.mainFrame = mainFrame;
        this.complaintService = complaintService;
        setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel();
        JButton registerComplaintButton = new JButton("Register New Complaint");
        JButton viewMyComplaintsButton = new JButton("View My Complaints");
        JButton logoutButton = new JButton("Logout");
        topPanel.add(registerComplaintButton);
        topPanel.add(viewMyComplaintsButton);
        topPanel.add(logoutButton);
        complaintsArea = new JTextArea(15, 50);
        complaintsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(complaintsArea);
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        registerComplaintButton.addActionListener(e -> {
            String category = JOptionPane.showInputDialog(this, "Enter complaint category:");
            String description = JOptionPane.showInputDialog(this, "Enter complaint description:");
            if (category != null && description != null && !category.trim().isEmpty() && !description.trim().isEmpty()) {
                String id = "C" + System.currentTimeMillis();
                Complaint complaint = new Complaint(id, currentUser, category, description, "Pending");
                complaintService.registerComplaint(complaint);
                JOptionPane.showMessageDialog(this, "Complaint registered successfully!");
                viewMyComplaintsButton.doClick();
            } else {
                JOptionPane.showMessageDialog(this, "Category and Description cannot be empty.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });
        viewMyComplaintsButton.addActionListener(e -> {
            try {
                String complaints = complaintService.viewMyComplaints(currentUser);
                complaintsArea.setText(complaints);
            } catch (FileAccessException ex) {
                JOptionPane.showMessageDialog(this, "Could not retrieve complaints.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        logoutButton.addActionListener(e -> {
            this.currentUser = null;
            complaintsArea.setText("");
            mainFrame.showPanel("LOGIN");
        });
    }
    public void setCurrentUser(String username) {
        this.currentUser = username;
        complaintsArea.setText("Welcome, " + username + "!\nClick 'View My Complaints' to see your records.");
    }
}