import javax.swing.*;
import java.awt.*;
public class AdminPanel extends JPanel {
    private MainFrame mainFrame;
    private ComplaintService complaintService;
    public AdminPanel(MainFrame mainFrame, ComplaintService complaintService) {
        this.mainFrame = mainFrame;
        this.complaintService = complaintService;
        setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel();
        JButton viewAllButton = new JButton("View All Complaints");
        JButton updateStatusButton = new JButton("Update Status");
        JButton logoutButton = new JButton("Logout");
        topPanel.add(viewAllButton);
        topPanel.add(updateStatusButton);
        topPanel.add(logoutButton);
        JTextArea complaintsArea = new JTextArea(15, 50);
        complaintsArea.setText("Welcome, Admin!\nClick 'View All Complaints' to load records.");
        complaintsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(complaintsArea);
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        viewAllButton.addActionListener(e -> {
            try {
                String allComplaints = complaintService.viewAll();
                complaintsArea.setText("ID,Username,Category,Description,Status\n-------------------------------------\n" + allComplaints);
            } catch (FileAccessException ex) {
                JOptionPane.showMessageDialog(this, "Could not retrieve complaints.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        updateStatusButton.addActionListener(e -> {
            String complaintId = JOptionPane.showInputDialog(this, "Enter Complaint ID to update:");
            if (complaintId == null || complaintId.trim().isEmpty()) return;
            String newStatus = JOptionPane.showInputDialog(this, "Enter new status:");
            if (newStatus == null || newStatus.trim().isEmpty()) return;
            try {
                if (complaintService.updateStatus(complaintId, newStatus)) {
                    JOptionPane.showMessageDialog(this, "Status updated successfully!");
                    viewAllButton.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "Complaint ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (FileAccessException ex) {
                JOptionPane.showMessageDialog(this, "Error updating status.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        logoutButton.addActionListener(e -> {
            complaintsArea.setText("");
            mainFrame.showPanel("LOGIN");
        });
    }
}