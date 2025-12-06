public class AdminService extends UserService {
    @Override
    public boolean login(String u, String p) {
        return u.equals("admin") && p.equals("admin123");
    }
}
