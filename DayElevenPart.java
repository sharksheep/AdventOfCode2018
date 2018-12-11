import java.util.HashMap;
import java.util.Iterator;

public class DayElevenPart {
    public final static int PUZZLE_INPUT = 9110;

    public final static int X_SIZE = 300;

    public final static int Y_SIZE = 300;

    public HashMap<String, Integer> valuesMap = new HashMap<>();

    public HashMap<String, Integer> totalPowerMap = new HashMap<>();

    public int[][] totalPowerArray = new int[301][301];

    public void resolve1(int number) {
        for (int i = 1; i < X_SIZE + 1; i++) {
            for (int j = 1; j < Y_SIZE + 1; j++) {
                String key = i + "," + j;
                int powerLevel = getPowerLevel(i, j, PUZZLE_INPUT);
                totalPowerArray[j][i] = powerLevel + totalPowerArray[j - 1][i] + totalPowerArray[j][i - 1] - totalPowerArray[j - 1][i - 1];
                // totalPowerArray[i-1][j-1] = powerLevel;
                valuesMap.put(key, powerLevel);
            }
        }
        int maxV = 0, maxSize = 0;
        String maxKey = null;
        for (int size = 1; size < number; size ++) {
            for (int i = size; i <= Y_SIZE  ; i++) {
                for (int j = size; j <= X_SIZE  ; j++) {
                    String key = (j - size + 1) + "," + (i - size + 1) + "," + (size);
                    // int totalPower = getTotalPower(j, i, size);
                    int totalPower = totalPowerArray[i][j] - totalPowerArray[i - size][j] 
                                     - totalPowerArray[i][j - size] + totalPowerArray[i - size][j - size];
                    if (maxV < totalPower) {
                        maxV = totalPower;
                        maxSize = size;
                        maxKey = key;
                    }
                    // System.out.println("\t\t\t"+key + ":" + totalPower);
                    // totalPowerMap.put(key, totalPower);
                }
            }
            System.out.println("size is:" + (size) + ",now max size is:" + maxSize + ", max value is: " + maxV + ",max key is:" + maxKey);
        }

        // findTheMaxValue(number);
    }

	private void findTheMaxValue(int num) {
		Iterator iterator = totalPowerMap.keySet().iterator();
        int maxPower = 0;
        String maxId = null;
        while(iterator.hasNext()) {
            String key = (String)iterator.next();
            int value = (Integer)totalPowerMap.get(key);
            // System.out.println(key + ":" + value);
            if (maxPower < value) {
                maxPower = value;
                maxId = key;
            }
        }
        // System.out.println("Size is: "+num+" Max Id is:(" + maxId + "), max value is:" + maxPower);
    }

    public int getTotalPower(int x, int y, int size) {
        int value = 0;
        long mxTime = 0;
        for (int i = y; i <= y + size; i++) {
            for (int j = x; j <= x + size; j++) {
                String key = j + "," + i;
                value = totalPowerArray[j-1][i-1] + value;
            }
        }
        return value;
    }

    public int getPowerLevel(int x, int y, int serialNo) {
        x = x + 10;
        int pStarts = x * y;
        int value = (pStarts + serialNo) * x;
        value = (value / 100) % 10 - 5;
        return value;
    }

    public static void main(String[] args) {
        DayElevenPart de = new DayElevenPart();
        long startTimes = System.nanoTime();
        // de.resolve1(3+1);
        long costTimes = System.nanoTime() - startTimes;
        // System.out.println("Cost 1 time is:" + costTimes + " ns"); 
        // de.totalPowerMap.clear();
        // de.valuesMap.clear();
        //参见算法wiki，summed area table
        //https://en.wikipedia.org/wiki/Summed-area_table
        de.resolve1(301);
        costTimes = System.nanoTime() - startTimes - costTimes;
        System.out.println("Cost 2 time is:" + costTimes + " ns");
    }

}