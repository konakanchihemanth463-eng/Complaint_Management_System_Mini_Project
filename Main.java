import java.util.*;
public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        UserService us = new UserService();
        AdminService admin = new AdminService();
        ComplaintService cs = new ComplaintService();
        while(true) {
            System.out.println("\n--- Complaint Management System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();
            if(ch == 1) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();
                try {
                    if(us.register(u,p))
                        System.out.println("User Registered Successfully!");
                    else
                        System.out.println("Username already exists!");
                } catch (FileAccessException e) {
                    System.out.println("Error during registration. Could not access user data.");
                    e.printStackTrace();
                }
            }else if(ch == 2) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();
                try {
                    if(us.login(u,p)) {
                    System.out.println("Login Successful!");
                    while(true) {
                        System.out.println("\n1. Register Complaint");
                        System.out.println("2. View My Complaints");
                        System.out.println("3. Logout");
                        System.out.print("Choice: ");
                        int c = sc.nextInt();
                        sc.nextLine();
                        if(c == 1) {
                            System.out.print("Category: ");
                            String cat = sc.nextLine();
                            System.out.print("Description: ");
                            String desc = sc.nextLine();
                            String id = "C" + System.currentTimeMillis();
                            Complaint comp = new Complaint(id, u, cat, desc, "Pending");
                            cs.registerComplaint(comp);
                            System.out.println("Complaint Registered!");
                        }else if(c == 2) {
                            try {
                                String myComplaints = cs.viewMyComplaints(u);
                                System.out.println(myComplaints);
                            } catch (FileAccessException e) {
                                System.out.println("Error viewing your complaints. Could not access data.");
                                e.printStackTrace();
                            }
                        }else break;
                    }
                }
                } catch (UserNotFoundException | IncorrectPasswordException e) {
                    System.out.println(e.getMessage());
                } catch (FileAccessException e) {
                    System.out.println("Error accessing user data. Please contact support.");
                    e.printStackTrace();
                }
            }else if(ch == 3) {
                System.out.print("Admin Username: ");
                String u = sc.nextLine();
                System.out.print("Admin Password: ");
                String p = sc.nextLine();
                if(admin.login(u,p)) {
                    while(true) {
                        System.out.println("\n--- Admin Menu ---");
                        System.out.println("1. View All Complaints");
                        System.out.println("2. Update Complaint Status");
                        System.out.println("3. Logout");
                        System.out.print("Choice: ");
                        int c = sc.nextInt();
                        sc.nextLine();
                        if(c == 1) {
                            try {
                                String allComplaints = cs.viewAll();
                                System.out.println("\nAll Complaints:");
                                System.out.println("ID,Username,Category,Description,Status");
                                System.out.println("-------------------------------------");
                                System.out.println(allComplaints);
                            } catch (FileAccessException e) {
                                System.out.println("Error viewing all complaints. Could not access data.");
                                e.printStackTrace();
                            }
                        }else if(c == 2) {
                            System.out.print("Enter Complaint ID: ");
                            String id = sc.nextLine();
                            System.out.print("Enter New Status: ");
                            String s = sc.nextLine();
                            try {
                                if(cs.updateStatus(id, s))
                                    System.out.println("Updated!");
                                else
                                    System.out.println("ID NOT FOUND!");
                            } catch (FileAccessException e) {
                                System.out.println("Error updating complaint status.");
                                e.printStackTrace();
                            }
                        }else break;
                    }
                } else {
                    System.out.println("Invalid Admin Credentials!");
                }
            }else {
                System.out.println("Exiting...");
                break;
            }
        }
    }
}
