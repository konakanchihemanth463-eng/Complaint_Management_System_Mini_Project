import java.io.*;
public class TextFileManager extends FileManager {
    @Override
    void write(String fileName, String data) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(data);
            bw.newLine();
        }catch(Exception e) { System.out.println("Error writing file"); }
    }
    @Override
    String read(String fileName) throws FileAccessException {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }catch(Exception e) {
            throw new FileAccessException("Error reading file: " + fileName, e);
        }
        return sb.toString();
    }
}
