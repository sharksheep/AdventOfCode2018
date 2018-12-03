import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class DayTwoPartTwo extends FileReadContext {

    public ArrayList<String> stringList = new ArrayList<>();

    public String stringValue = new String();

    public char[] diffs = new char[2];

    public int stringPosition = -1;

    public int lineFirst = -1;

    public int lineSecond = -1;

    @Override
    public String getFileName() {
        return "day202.txt";
    }

    @Override
    public String readPuzzleLine(String line) {
        stringList.add(line);
        return line;
    }

    @Deprecated
    public void compareString(ArrayList<String> strings) {
        int size = strings.size();
        int index = 0;
        for (String var : strings) {
            System.out.println("--------------------");
            System.out.print(var);
            for (int i = index + 1; i < size; i++) {
                String secondString = strings.get(i);
                System.out.println("\t" + secondString);
                System.out.println("==========");
                boolean isFind = compareSplitString(var, secondString);
                System.out.println("==========");
                System.out.println("is find? " + String.valueOf(isFind));
                if (isFind) {
                    lineFirst = index;
                    lineSecond = i;
                    return;
                }
            }
            index ++;
            System.out.println("--------------------");

        }
    }

    @Deprecated
    public boolean compareSplitString(String firstString, String secondString) {
        int length = firstString.length();
        for (int index = 0; index < length; index++) {
            char indexChar = firstString.charAt(index);
            char nextIndexChar = secondString.charAt(index);
            System.out.println(indexChar + "," + nextIndexChar);
            // System.out.println(indexChar);
            if (indexChar != nextIndexChar) {
                String item1 = firstString.substring(0, index);
                String item2 = firstString.substring(index + 1);
                String nextItem1 = secondString.substring(0, index);
                String nextItem2 = secondString.substring(index + 1);
                // System.out.print("secondString: " + secondString);
                System.out.print("item1: " + item1);
                System.out.println("\titem2: " + item2);
                System.out.print("nextItem1: " + nextItem1);
                System.out.println("\tnextItem2: " + nextItem2);
                // if (secondString.contains(item1) && secondString.contains(item2)) {
                if (item1.equals(nextItem1) && item2.equals(nextItem2)) {
                    diffs[0] = indexChar;
                    diffs[1] = nextIndexChar;
                    stringPosition = index;
                    stringValue = item1 + item2;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }



    /*
     * High performance version
     * the max complexity is O[len*size*(size-1)]
     **/
    public void compareStringV2(ArrayList<String> strings) {
        int length = strings.get(0).length();
        int size = strings.size();
        int index = 0;
        for (int i = 0; i < length; i++) {
            index = i;
            for (int j = 0; j < size; j++) {
                String firstString = strings.get(j);
                String preString = firstString.substring(0, index);
                String endString = firstString.substring(index + 1);
                System.out.println("\t"+preString + "\t" + endString);
                for (int k = j + 1; k < size; k++) {
                    String secondString = strings.get(k);
                    if (secondString.contains(preString) && secondString.contains(endString)) {
                        stringValue = preString + endString;
                        stringPosition = index;
                        diffs[0] = firstString.charAt(index);
                        diffs[1] = secondString.charAt(index);
                        return;
                    }
                }
            }
        }
        
    }

    /**
     * hash find the same one, the higher than V2 method.
     */
    public void compareStringV3(ArrayList<String> strings) {
        int length = strings.get(0).length();
        int size = strings.size();
        HashSet<String> valueSet = new HashSet<>();
        int index = 0;
        for (int i = 0; i < length; i++) {
            index = i;
            for (int j = 0; j < size; j++) {
                String firstString = strings.get(j);
                String preString = firstString.substring(0, index);
                String endString = firstString.substring(index + 1);
                System.out.println("\t"+preString + "\t" + endString);
                String hashString = preString + endString;
                if (valueSet.contains(hashString)) {
                    stringValue = hashString;
                    stringPosition = index;
                    diffs[0] = firstString.charAt(index);
                    // diffs[1] = secondString.charAt(index);
                    return;
                } else {
                    valueSet.add(hashString);
                }
            }
        }
        
    }

    @Override
    public void doFileLogic() throws IOException {
        //Must call
        super.doFileLogic();
        //Cost much more time
        // compareString(stringList);
        // much better than V1
        // compareStringV2(stringList);
        // now the best
        compareStringV3(stringList);
    }

    public static void main(String[] args) {
        try {
            long startTimes = System.nanoTime();
            DayTwoPartTwo similarStringBox = new DayTwoPartTwo();
            similarStringBox.doFileLogic();
            System.out.println("Value is:" + similarStringBox.stringValue);
            System.out.println("Different char is:" + similarStringBox.diffs[0] + "," + similarStringBox.diffs[1]);
            System.out.println("Char position is:" + similarStringBox.stringPosition);
            System.out.println("String line first is:" + similarStringBox.lineFirst);
            System.out.println("String line second is:" + similarStringBox.lineSecond);
            long costTimes = System.nanoTime() - startTimes;
            System.out.println("Cost time is:" + costTimes + " ns");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}