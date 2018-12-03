import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class DayTwoInventory extends FileReadContext{

    public HashMap<String, HashMap<Character, Integer>> inventoryList = new HashMap<>();
    
    @Override
    public String getFileName() {
        return "day201.txt";
    }

    public int getBoxIdsChecksumFromFile() {
        try {
            doFileLogic();
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return computeBoxIdsChecksum(inventoryList);
    }

    @Override
    public String readPuzzleLine(String line) {
        System.out.println("-----------------------------");
        System.out.println(line);
        inventoryList.put(line, getRepeatCountArray(line));
        return line;
    }

    public HashMap<Character, Integer> getRepeatCountArray(String targeString) {
        HashMap<Character, Integer> values = new HashMap<>();
        char[] charArray = targeString.toCharArray();
        for(int i = 0; i<charArray.length; i++) {
            Character item = charArray[i];
            if (values.containsKey(item)) {
                int counts = values.get(item);
                counts ++;
                values.put(item, counts);
                System.out.println(item + " " + counts);
            } else {
                values.put(item, 1);
            }
        }
        System.out.println("------------------------------");
        return values;
    }

    public int computeBoxIdsChecksum(HashMap<String, HashMap<Character, Integer>> inventoryList) {
        HashMap<Integer, Integer> timesList = new HashMap<>();
        Iterator iterator = inventoryList.keySet().iterator();
        HashSet<Integer> valueListSet = new HashSet<>();
        HashMap<Character, Integer> valueMap = new HashMap<>();
        while(iterator.hasNext()) {
            String key = (String)iterator.next();
            valueMap.clear();
            valueMap.putAll(inventoryList.get(key));
            valueListSet.clear();
            valueListSet.addAll(valueMap.values());
            for (Integer var : valueListSet) {
                if (var > 1 ) {
                    if (timesList.containsKey(var)) {
                        int counts = timesList.get(var);
                        timesList.put(var, ++counts);
                    } else {
                        timesList.put(var, 1);
                    }
                }
            }
        }

        int values = 1;
        for (Integer var : timesList.values()) {
            values *= var;
        }
        return values;
    }

    public static void main(String[] args) {
        long startTimes = System.nanoTime();
        DayTwoInventory idBox = new DayTwoInventory();
        int values = idBox.getBoxIdsChecksumFromFile();
        System.out.println("the values is :" + values);
        long costTimes = System.nanoTime() - startTimes;
        System.out.println("Cost time is:" + costTimes + " ns");
        
    }


}