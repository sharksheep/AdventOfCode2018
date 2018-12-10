import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class DayNinePart {
    public static final String INPUT = "405 players; last marble is worth 70953 points";
    public static final int PLAYERS = 405;
    public static int POINTS = 70953;
    // public static final int PLAYERS = 17;
    // public static final int POINTS = 1104;
    // public static final int PLAYERS = 9;
    // public static final int POINTS = 93;
    // public static final int PLAYERS = 10;
    // public static final int POINTS = 1618;

    public LinkedList<Integer> dataList = new LinkedList<>();

    public HashMap<Integer, Integer> scoreMap = new HashMap<>();

    public int maxValue;

    public void result1() {
        int pointerIndex = 1;
        dataList.add(0);
        for (int j = 1; j <= POINTS; j++) {
            // if (playerIndex > PLAYERS) {
            //     playerIndex = 1;
            // }
            // System.out.println(playerIndex);
            if (j % 23 == 0) {
                int score = 0;
                int key = j % PLAYERS;
                if (scoreMap.containsKey(key)) {
                    score = scoreMap.get(key);
                }
                score += j;
                // System.out.println("pointerIndex->" + pointerIndex);
                int tempIndex = pointerIndex - 7 - 2;
                if (tempIndex <= 0) {
                    tempIndex = dataList.size() + tempIndex;
                }
                // System.out.println("pointerIndex->" + tempIndex);
                int value = score + dataList.get(tempIndex);
                scoreMap.put(key, value);
                // System.out.println("Score->" + "Player:" + key + "->" + value + "->" + dataList);
                dataList.remove(tempIndex);
                pointerIndex = tempIndex + 2;
                // System.out.println("Player "+playerIndex+"-"+pointerIndex+"->"+dataList);
            } else {
                int size = dataList.size();
                if (pointerIndex == size) {
                    dataList.add(j);
                    // dataList.addLast(j);
                } else if (pointerIndex > size) {
                    pointerIndex = size % 2;
                    if (pointerIndex == 0) {
                        pointerIndex++;
                    }
                    dataList.add(pointerIndex, j);
                } else {
                    dataList.add(pointerIndex, j);
                }
                // System.out.println("Player "+playerIndex+"-"+pointerIndex+"->"+dataList);
                pointerIndex += 2;
            }
        }
        maxValue = getMaxValue();
    }

    public int getMaxValue() {
        Iterator iterator = scoreMap.keySet().iterator();
        int maxValue = 0, player = 0;
        while (iterator.hasNext()) {
            int key = (Integer)iterator.next();
            int value = scoreMap.get(key);
            if (value > maxValue) {
                maxValue = value;
                player = key;
            }
        }
        return maxValue;
    }

    public void result2() {
        POINTS *= 100;
        dataList.clear();
        scoreMap.clear();
        result1();
    }
 
    public static void main(String[] args) {
        long startTimes = System.nanoTime();
        DayNinePart dnp = new DayNinePart();
        dnp.result1();
        System.out.println("Part 1 get the highest score: " + dnp.maxValue);
        long costTimes = System.nanoTime() - startTimes;
        System.out.println("Cost time 1 is:" + costTimes + " ns");
        dnp.result2();
        System.out.println("Part 2 get the highest score: " + dnp.maxValue);
        costTimes = System.nanoTime() - startTimes;
        System.out.println("Cost time is:" + costTimes + " ns");
        DayNinePart.Day9 day9 = new DayNinePart.Day9();
        long r1 = (long)day9.part1();
        System.out.println(r1);
    }


 static class Day9 {

        int players = DayNinePart.PLAYERS;
        int end = DayNinePart.POINTS;
    
    
        class CircleDeque<T> extends ArrayDeque<T> {
            void rotate(int num) {
                if (num == 0) return;
                if (num > 0) {
                    for (int i = 0; i < num; i++) {
                        T t = this.removeLast();
                        this.addFirst(t);
                    }
                } else {
                    for (int i = 0; i < Math.abs(num) - 1; i++) {
                        T t = this.remove();
                        this.addLast(t);
                    }
                }
    
            }
        }
    
        long game(int players, int end) {
            CircleDeque<Integer> circle = new CircleDeque<>();
            circle.addFirst(0);
            long[] scores = new long[players];
            for (int i = 1; i <= end; i++) {
                if (i % 23 == 0) {
                    circle.rotate(-7);
                    scores[i % players] += i + circle.pop();
    
                } else {
                    circle.rotate(2);
                    circle.addLast(i);
                }
            }
            return Arrays.stream(scores).max().getAsLong();
        }
    
        public Object part1() {
            return game(players, end);
        }
    
        public Object part2() {
            return game(players, end * 100);
        }
    
        public void parse() {
            String[] split = input.get(0).split(" ");
            players = Integer.parseInt(split[0]);
            end = Integer.parseInt(split[6]);
        }
    
    }

}