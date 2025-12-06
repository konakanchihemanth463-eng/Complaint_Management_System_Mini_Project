public class LoadingThread extends Thread {
    private FileManager fm;
    public LoadingThread(FileManager fm) {
        this.fm = fm;
    }
    @Override
    public void run() {
        try {
            fm.read("users.txt");
            fm.read("complaints.txt");
        } catch (FileAccessException e) {
            System.err.println("Error during file pre-loading:");
            e.printStackTrace();
        }
    }
}