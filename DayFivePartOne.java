import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class DayFivePartOne extends FileReadContext {
    public Stack<Integer> stackData = new Stack<>();
    public static HashMap<Integer, Stack<Integer>> stackData2 = new HashMap<>();
    public int minSize = Integer.MAX_VALUE;

    static {
        for (int i = 'A'; i <= 'Z'; i++) {
            stackData2.put(i, new Stack<Integer>());
        }
    }

    @Override
    public String getFileName() {
        return "day501.txt";
    }

    @Override
    public String readPuzzleLine(String line) {
        // Do nothing with this time
        return null;
    }

    @Override
    protected boolean isReadLine() {
        return false;
    }

    @Override
    public void readChar(int charsequence) {
        reductionElement(stackData, charsequence);
        removeOneElementStack(charsequence);
    }

    public int reductionElement(Stack<Integer> stack, int element) {
        int len = stack.size();
        if (len != 0) {
            int topElement = stack.peek();
            if (Math.abs(topElement - element) == 32) {
                stack.pop();
            } else {
                stack.add(element);
            }
            return 0;
        } else {
            stack.add(element);
            return -1;
        }
    }

    @Override
    public void doFileLogic() throws IOException {
        super.doFileLogic();
        // System.out.println(stackData.size());
        Iterator iterator = stackData2.keySet().iterator();
        while(iterator.hasNext()) {
            Integer key = (Integer)iterator.next();
            Stack<Integer> stack2 = stackData2.get(key);
            int size = stack2.size();
            if (size < minSize) {
                minSize = size;
            }
            System.out.println("Key:" + (char)key.intValue() + ",size:" + size);
        }
    }

    public int getUpperKey(Integer element) {
        return element > 90 ? element - 32 : element;
    }

    public void removeOneElementStack(int element) {
        if (!stackData2.containsKey(getUpperKey(element))) {
            Stack<Integer> tempStack = new Stack<>();
            stackData2.put(getUpperKey(element), tempStack);
            System.out.println("None element " + (char)element);
        } else {
        }
        Iterator iterator = stackData2.keySet().iterator();
        while(iterator.hasNext()) {
            Integer key = (Integer)iterator.next();
            if (key != getUpperKey(element)) {
                reductionElement(stackData2.get(key), element);
            }
        }
    }

    public static void main(String[] args) {
        DayFivePartOne pfpo = new DayFivePartOne();
        try {
            long startTimes = System.nanoTime();
            pfpo.doFileLogic();
            System.out.println("First resolution is " + pfpo.stackData.size());
            System.out.println("Second resolution is " + pfpo.minSize);
            long costTimes = System.nanoTime() - startTimes;
            System.out.println("Cost time is:" + costTimes + " ns");
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}