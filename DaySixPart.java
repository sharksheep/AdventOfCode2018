import java.io.IOException;

public class DaySixPart extends FileReadContext {
    @Override
    public String getFileName() {
        return "day601.txt";
    }

    @Override
    public String readPuzzleLine(String line) {
        return null;
    }

    @Override
    public void doFileLogic() throws IOException {
        super.doFileLogic();
    }

    public static void main(String[] args) {
        DaySixPart dsp = new DaySixPart();
        try {
            dsp.doFileLogic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}