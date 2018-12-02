import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public abstract class FileReadContext {

    public static final String PUZZLE_PATH = new File("").getAbsolutePath() + "/adventofcode/assets/";
    
    /**
     * Get file name from assets folder
     */
    public abstract String getFileName();

    /**
     * Get file from file path
     */
    public File getPuzzleFile() {
        return new File(PUZZLE_PATH + getFileName());
    }

    /**
     * read file with buffer reader
     */
    private void readFileContent() throws IOException{
        FileReader fReader = new FileReader(getPuzzleFile());
        BufferedReader bufferedReader = new BufferedReader(fReader);
        String line = null;
        while((line = bufferedReader.readLine()) != null) {
            readPuzzleLine(line);
        }
        bufferedReader.close();
        fReader.close();
    }

    /**
     * Read file content with every lines.
     */
    public abstract String readPuzzleLine(String line); 

    public void doFileLogic() throws IOException {
        readFileContent();
    }

}