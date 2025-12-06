import java.io.*;
public class UserService {
    FileManager fm = new TextFileManager();
    public boolean register(String username, String password) throws FileAccessException {
        String data = fm.read("users.txt");
        String[] rows = data.split("\n");
        for (String r : rows) {
            if (r.trim().isEmpty()) continue;
            String[] parts = r.split(",");
            if (parts.length > 0 && parts[0].equals(username)) {
                return false;
            }
        }
        fm.write("users.txt", username + "," + password);
        return true;
    }
    public boolean login(String username, String password) throws FileAccessException, UserNotFoundException, IncorrectPasswordException {
        String data = fm.read("users.txt");
        String[] rows = data.split("\n");
        boolean userFound = false;
        for(String r : rows) {
            if (r.trim().isEmpty()) continue;
            String[] parts = r.split(",");
            if(parts.length == 2 && parts[0].equals(username)) {
                userFound = true;
                if(parts[1].equals(password)) {
                    return true;
                } else {
                    throw new IncorrectPasswordException("Incorrect password for user: " + username);
                }
            }
        }
        throw new UserNotFoundException("User not found: " + username);
    }
}
