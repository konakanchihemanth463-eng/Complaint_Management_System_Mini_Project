import java.io.*;
public abstract class FileManager {
    abstract void write(String fileName, String data);
    abstract String read(String fileName) throws FileAccessException;
}