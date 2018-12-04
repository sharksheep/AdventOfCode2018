import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class DayFourFindGuard extends FileReadContext {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    class GuardItem {
        long timeStamp;
        String statusValue;

        public GuardItem(long timeStamp, String sValue) {
            this.timeStamp = timeStamp;
            this.statusValue = sValue;
        }

        @Override
        public String toString() {
            return "\n["+sdf.format(new Date(timeStamp))+"]" + statusValue;
        }
    }
    
    public ArrayList<GuardItem> sourceList = new ArrayList<>();

    public HashMap<String, Integer[]> dataList = new HashMap<>();

    public int resolution1 = 0;
    public int resolution2 = 0;

    String maxResult2Id = "";
    int maxResult2Times = -1;

    @Override
    public String getFileName() {
        return "day401.txt";
    }

    @Override
    public String readPuzzleLine(String line) {
        try {
            String dateTime = line.substring(1, line.indexOf("]"));
            long timeStamp = sdf.parse(dateTime).getTime();
            String valString = line.substring(line.indexOf("]") + 2);
            sourceList.add(new GuardItem(timeStamp, valString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }

    public void adapterData() {
        String ID = "";
        int sleepStatus = -1;
        int lastMinutes = 0;
        for (GuardItem var : sourceList) {
            int size = 0;
            if (var.statusValue.startsWith("Guard")) {
                String newId = var.statusValue.split(" ")[1];
                if (!newId.equals(ID)) {
                    ID = newId;
                }
                sleepStatus = -1;
                lastMinutes = 0;
                
            } else if (var.statusValue.startsWith("falls")) {
                // if (sleepStatus != 1) {
                lastMinutes = getMinute(var.timeStamp);
                // }
                sleepStatus = 1;
            } else /*if (var.statusValue.startsWith("wakes"))*/ {
                int min = getMinute(var.timeStamp);
                if (sleepStatus != 0) {
                    // size = minutes - lastMinutes;
                    Integer[] minutes;
                    if (dataList.containsKey(ID)){
                        minutes = dataList.get(ID);
                    } else {
                        minutes = new Integer[60];
                        dataList.put(ID, minutes);
                    }
                    for (int i = lastMinutes; i < min; i++) {
                        minutes[i] = (minutes[i] == null ? 0 : minutes[i]) + 1;
                    }
                }
                lastMinutes = min;
                sleepStatus = 0;
            }
        }
    }

    public void getResult1() throws Exception{
        String maxId = "";
        int maxValue = 0;
        int maxTimes = 0;
        int maxMinutes = -1;
        Iterator iterator = dataList.keySet().iterator();
        
        while(iterator.hasNext()) {
            String ids = (String)iterator.next();
            Integer[] vals = dataList.get(ids);
            int tempMax = 0;
            int tempMaxTimes = 0;
            for (int i = 0; i < 60; i++) {
                int tempTimes = vals[i] == null ? 0 : vals[i];
                tempMax += tempTimes;
                if (tempTimes > tempMaxTimes) {
                    tempMaxTimes = tempTimes;
                }

                //for result2
                if (maxResult2Times < tempTimes) {
                    maxResult2Times = tempTimes;
                    maxResult2Id = ids;
                }
            }
            if (tempMax > maxValue) {
                maxId = ids;
                maxValue = tempMax;
                maxTimes = tempMaxTimes;
            }

            // System.out.println("Result2 -> " + maxResult2Id + ", times->" + maxResult2Times);
        }

        Integer[] target = dataList.get(maxId);
        for (int i = 0; i < target.length; i++) {
            if (target[i].intValue() == maxTimes) {
                maxMinutes = i;
                break;
            }
        }
        resolution1 = Integer.parseInt(maxId.substring(1)) * maxMinutes;
        System.out.println("MaxId is:" + maxId + ", maxValue is:" + maxValue + ", maxTimes is:" + maxTimes + ", max minutes is :" + maxMinutes);
    }


    /**
     * @return the resolution1
     */
    public void getResolution2() throws Exception {
        int maxResult2Index = -1;
        Integer[] target = dataList.get(maxResult2Id);
        for (int i = 0; i < target.length; i++) {
            Integer tValue = target[i] == null ? 0 : target[i];
            if (tValue.intValue() == maxResult2Times) {
                maxResult2Index = i;
                break;
            }
        }
        resolution2 = Integer.parseInt(maxResult2Id.substring(1)) * maxResult2Index;
        System.out.println("Resolution 2 is " +resolution2 +"->id is " + maxResult2Id + ",index is ->" + maxResult2Index);
    }

    @Override
    public void doFileLogic() throws IOException {
        super.doFileLogic();
        sourceList.sort(new Comparator<GuardItem>() {
            @Override
            public int compare(GuardItem o1, GuardItem o2) {
                if (o1.timeStamp > o2.timeStamp) {
                    return 1;
                } else if (o1.timeStamp < o2.timeStamp) {
                    return -1;
                }
                return 0;
            }
        });
        adapterData();
        try {
            getResult1();
            getResolution2();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(sourceList);
        // System.out.println(Arrays.asList(dataList.get("#401")));
    }

    public static void main(String[] args) {
        try {
            long startTimes = System.nanoTime();
            DayFourFindGuard dffg = new DayFourFindGuard();
            dffg.doFileLogic();
            System.out.println("resolution 1 is:" + dffg.resolution1);
            System.out.println("resolution 2 is:" + dffg.resolution2);
            long costTimes = System.nanoTime() - startTimes;
            System.out.println("Cost time is:" + costTimes + " ns");
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
        }
    }

}