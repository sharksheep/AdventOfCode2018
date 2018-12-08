import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;
import java.util.TreeSet;

public class DaySevenPart extends FileReadContext {

    public ArrayList<StepItem> sourceData = new ArrayList<>();
    // public HashMap<Integer, List> dataList = new HashMap();

    public HashMap<Integer, Integer> nodeCounts = new HashMap<>();

    public HashSet<Integer> removeZeroNodes = new HashSet<>();

    public class StepItem {
        public char left;
        public char right;

        public StepItem(char left, char right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "["+left+"-->"+right+"]\n";
        }
    }

    @Override
    public String getFileName() {
        return "day701.txt";
    }

    @Override
    public String readPuzzleLine(String line) {
        char left = line.charAt(5);//first char
        char right = line.charAt(line.lastIndexOf("step") + 5);
        // List<Integer> nodes = null;
        // if (dataList.containsKey(Integer.valueOf(left))) {
        //     nodes = dataList.get(Integer.valueOf(left));
        // } else {
        //     nodes = new ArrayList<>();
        //     dataList.put(Integer.valueOf(left), nodes);
        // }
        // nodes.put(Integer.valueOf(right));

        //zero node map
        if (!nodeCounts.containsKey((int)left)) {
            nodeCounts.put((int) left, 0);
        }
        if (!nodeCounts.containsKey((int)right)) {
            nodeCounts.put((int) right, 1);
        } else {
            int count = nodeCounts.get((int)right);
            nodeCounts.put((int) right, count + 1);
        }
        StepItem si = new StepItem(left, right);
        sourceData.add(si);
        return null;
    }

    @Override
    public void doFileLogic() throws IOException {
        super.doFileLogic();
        // Collections.sort(sourceData, );
        sortList();
        //find the first zero node
        ArrayList<Integer> fNodes = new ArrayList<>();
        Stack<Integer> nodeStack = new Stack<>();
        Iterator iterator =  nodeCounts.keySet().iterator();
        while(iterator.hasNext()) {
            Integer key = (Integer) iterator.next();
            int count = nodeCounts.get(key);
            if (count == 0) {
                fNodes.add(key);
                nodeStack.push(key);
            }
        }
        Collections.sort(fNodes);
        System.out.println(fNodes);
        for (int i=fNodes.size() -1; i >=0; i--) {
            nodeStack.push(fNodes.get(i));
        }
        StringBuilder sBuilder = new StringBuilder();
        System.out.println(nodeCounts);
        while (!nodeStack.empty()) {
        // for (Integer var : fNodes) {
            Integer pop = nodeStack.pop();
            sBuilder.append((char) (pop.intValue()));
            for (StepItem node : sourceData) {
                if (pop == node.left) {
                    int count = nodeCounts.get((int)node.left);
                    if (count == 0) {
                        nodeStack.push((int)node.right);
                        System.out.println("push to stack------------>" + ((char)node.right));
                        break;
                    }
                    System.out.println(((char)node.right) + "----->count = " + count);
                    count -= 1;
                    nodeCounts.put((int)node.right, count);
                    System.out.println(((char)pop.intValue()) + "-->" + ((char)node.right) + ", count = " + count);
                    
                    // Iterator iterator3 = nodeCounts.keySet().iterator();
                    // while(iterator3.hasNext()) {
                    //     Integer key = (Integer) iterator3.next();
                    //     int count = nodeCounts.get(key);
                    //     if (key == node.right) {
                    //         count -= 1;
                    //         System.out.println(((char)pop.intValue()) + "-->" + ((char)key.intValue()) + ", count = " + count);
                    //         nodeCounts.put(key, count);
                    //         if (count == 0) {
                    //             nodeStack.push(key);
                    //             System.out.println("push to stack------------>" + ((char)key.intValue()));
                    //             sBuilder.append((char)(key.intValue()));
                    //         }
                    //     }
                    // }
                }
            }
        }
        System.out.println(nodeCounts);
        System.out.println(sBuilder.toString());
        
        // Iterator iterator = dataList.keySet();
        // while(iterator.hasNext()) {
        //     Integer key = iterator.next();
        //     List<Integer> values = dataList.get(key);

        // }
        
    }

	private void sortList() {
		Collections.sort(sourceData, new Comparator<StepItem>() {
            @Override
            public int compare(StepItem o1, StepItem o2) {
                if (o1.left > o2.left) {
                    return 1;
                } else if (o1.left < o2.left) {
                    return -1;
                } else {
                    if (o1.right > o2.right) {
                        return 1;
                    } else if (o1.right < o2.right){
                        return -1;
                    }
                }
                return 0;
            }
        });
       
        System.out.println(sourceData);
    }

    public static void main(String[] args) {
        DaySevenPart dsp = new DaySevenPart();
        try {
            dsp.doFileLogic();
            //There have no result in this java method, I use python get the result
            // JKNSTHCBGRVDXWAYFOQLMPZIUE
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}