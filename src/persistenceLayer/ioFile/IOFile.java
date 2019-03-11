package persistenceLayer.ioFile;

import java.io.*;

/**
 * 这是一个专门用于读取、写入文件的类。
 * @author 软英1702 马洪升 20175188
 */
public class IOFile {
    /*
    这是用来读取文件的方法，我们将文件传入后，该方法读取文件的行数和列数，之后根据行数和列数创建相应的列表。
    最后将字符串存入列表，再将列表返回以便使用。
     */
    public static String[][] readFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedReader readers = new BufferedReader(new FileReader(file));
            String countLine;
            String line;
            int lineCount = 0;
            int rowCount = 0;
            while ((countLine = reader.readLine()) != null) {
                String[] readLine = countLine.split(" ");
                rowCount = readLine.length;
                lineCount++;
            }
            String[][] readResult = new String[lineCount][rowCount];
            int count = 0;
            while ((line = readers.readLine()) != null) {
                String[] readLine = line.split(" ");
                for(int i = 0; i < rowCount; i++){
                    readResult[count][i] = readLine[i];
                }
                count++;
            }
            return readResult;
        } catch (FileNotFoundException fnfe) {
            System.out.println("IO读中The file can't be found.");
        } catch (IOException ioe) {
            System.out.println("There is something wrong while reading the file.");
        }
        return new String[0][];
    }
    /*
    这是专门用来将字符串写入文件的方法，我们将需要写入文件的列表和被写入文件传入，之后该方法读取列表的行和列。
    按照行数和列数进行双循环，将字符串写入文件。
     */
    public static boolean writeFile(String[][] writeResult, File file){
        int lineCount = writeResult.length;
        int rowCount = writeResult[0].length;
        BufferedWriter writer;
        try{
            writer = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < lineCount; i++){
                for(int j = 0; j < rowCount - 1 ; j++){
                    writer.write(writeResult[i][j] + " ");
                }
                writer.write(writeResult[i][rowCount - 1]);
                if(i != lineCount - 1){
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();
            return true;
        }catch(FileNotFoundException fnfe){
            System.out.println("IO写中The file can't be found.");
            return false;
        } catch (IOException ioe) {
            System.out.println("There is something wrong while reading the file.");
            return false;
        }
    }
}
