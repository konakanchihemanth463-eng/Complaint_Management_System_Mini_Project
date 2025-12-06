public class Complaint {
    private String id;
    private String username;
    private String category;
    private String description;
    private String status;
    public Complaint(String id, String user, String cat, String desc, String status) {
        this.id = id;
        this.username = user;
        this.category = cat;
        this.description = desc;
        this.status = status;
    }
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
