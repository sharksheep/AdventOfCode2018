import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class DayOneFrequency extends FileReadContext{
    public ArrayList<Integer> itemsList = new ArrayList<>();
    public HashSet<Integer> hashSet = new HashSet<>();

    public int valueSum = 0;

    public int firstTwice = 0;

    @Override
    public String getFileName() {
        return "day101.txt";
    }

    @Override
    public String readPuzzleLine(String line) {
        Integer item = Integer.parseInt(line);
        itemsList.add(item);
        return line;
    }

    @Override
    public void doFileLogic() throws IOException {
        super.doFileLogic();
        firstResolve();
        secondResolve();
    }

    private void firstResolve() {
		for (Integer var : itemsList) {
            valueSum += var;
            System.out.print(var);
            System.out.println("\t" + valueSum);
        }
    }

	private void secondResolve() {
        int sum = 0;
        int size = itemsList.size();
        
		for (int i = 0;;) {
            sum += itemsList.get(i);
            //Second part resolve
            if (hashSet.contains(sum)) {
                firstTwice = sum;
                System.out.println("twich is " + firstTwice);
                return;
            }
            hashSet.add(sum);
            i++;
            if (i == size) {
                i = 0;
            }
        }
    }

    public static void main(String[] args) {
        try {
            DayOneFrequency dayOneFirst = new DayOneFrequency();
            dayOneFirst.doFileLogic();
            System.out.println(dayOneFirst.valueSum);
            System.out.println(dayOneFirst.firstTwice);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}