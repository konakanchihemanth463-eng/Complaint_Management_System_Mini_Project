import java.io.*;
import java.util.*;
public class ComplaintService {
    FileManager fm = new TextFileManager();
    public void registerComplaint(Complaint c) {
        String data = c.getId() + "," + c.getUsername() + "," + c.getCategory() + "," +
                      c.getDescription() + "," + c.getStatus();
        fm.write("complaints.txt", data);
    }
    public String viewMyComplaints(String username) throws FileAccessException {
        String[] rows = fm.read("complaints.txt").split("\n");
        StringBuilder myComplaints = new StringBuilder();
        myComplaints.append("ID | Category | Description | Status\n");
        myComplaints.append("-------------------------------------\n");
        for(String r : rows) {
            if(r.trim().isEmpty()) continue;
            String[] p = r.split(",");
            if(p[1].equals(username)) {
                myComplaints.append(p[0] + " | " + p[2] + " | " + p[3] + " | " + p[4] + "\n");
            }
        }
        return myComplaints.toString();
    }
    public String viewAll() throws FileAccessException {
        return fm.read("complaints.txt");
    }
    public boolean updateStatus(String id, String newStatus) throws FileAccessException {
        String[] rows = fm.read("complaints.txt").split("\n");
        StringBuilder newData = new StringBuilder();
        boolean found = false;
        for(String r : rows) {
            if(r.trim().isEmpty()) continue;
            String[] p = r.split(",");
            if(p[0].equals(id)) {
                p[4] = newStatus;
                found = true;
            }
            newData.append(String.join(",", p)).append("\n");
        }
        try(FileWriter fw = new FileWriter("complaints.txt", false)) {
            fw.write(newData.toString());
        } catch (IOException e) {
            throw new FileAccessException("Error writing to complaints.txt", e);
        }
        return found;
    }
}
