import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.io.File;
import java.util.Date;

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
        if (isReadLine()) {
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                readPuzzleLine(line);
            }
        } else {
            int var = -1;
            while((var = bufferedReader.read()) != -1 ) {
                readChar(var);
            }
        }
        bufferedReader.close();
        fReader.close();
    }

    protected boolean isReadLine() {
        return true;
    }

    public void readChar(int charsequence) {
        //nothing to do
    }

    /**
     * Read file content with every lines.
     */
    public abstract String readPuzzleLine(String line); 

    public void doFileLogic() throws IOException {
        readFileContent();
    }

    public int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public int getMinute(long timeMills) {
        return getMinute(new Date(timeMills));
    }

}