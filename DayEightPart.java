import java.io.IOException;
import java.util.ArrayList;

public class DayEightPart extends FileReadContext {

    public ArrayList<Integer> dataList = new ArrayList<>();

    public ArrayList<Node> result1 = new ArrayList<>();

    public String tempStr = "";

    public int index = 0;

    public int sumValue = 0;

    class Node {
        ArrayList<Node> childs;
        int[] metadata;

        public Node(int childs, int metadataSize) {
            this.childs = new ArrayList<>(childs);
            this.metadata = new int[metadataSize];
        }

        public int getNodeValue() {
            int value = 0;
            if (childs.size() == 0) {
                for (int i = 0; i < metadata.length; i++) {
                    // System.out.println(metadata[i] + "\t" + childs.size() + "\t" + value);
                    value += metadata[i];
                }
                return value;
            }
            for (int i = 0; i < metadata.length; i++) {
                int j = metadata[i];
                if (j <= childs.size()) {
                    // System.out.println(metadata[i] + "\t" + childs.size() + "\t" + value);
                    value += childs.get(j-1).getNodeValue();
                } else {
                    value += 0;
                }
            }
            return value;
        }

        public int sum() {
            int sumV = 0;
            for (Node var : childs) {
                sumV += var.sum();
            }
            for (int i = 0; i < metadata.length; i++) {
                sumV += metadata[i];
            }
            return sumV;
        }

        @Override
        public String toString() {
            return "Node->" + childs.size() + ", childs - >" + childs + ", metadata-->" + metadata;
        }
        
    }

    @Override
    public String getFileName() {
        return "day801.txt";
    }

    @Override
    public String readPuzzleLine(String line) {
        return null;
    }

    @Override
    protected boolean isReadLine() {
        return false;
    }

    @Override
    public void readChar(int charsequence) {
        if (charsequence != ' ') {
            String s = "" + (char) charsequence;
            tempStr += s;
        } else {
            // System.out.print(tempStr + " ");
            dataList.add(Integer.parseInt(tempStr));
            tempStr = "";
        }
    }

    public Node resolve1() {
        // System.out.println("-------------index" + index);
        // if (index == dataList.size() - 1) {
        //     return new Node(0, 0);
        // }
        int child = dataList.get(index++);
        // System.out.println("index" + index);
        int metadataSize = dataList.get(index++);
        // System.out.println("index" + index);
        Node node = new Node(child, metadataSize);
        // System.out.print("\nchild:" + child + ",metadataSize:" + metadataSize);
        for (int i = 0; i < child && index < dataList.size(); i++) {
            // System.out.println("index->" + index);
            node.childs.add(resolve1());
        }
        // System.out.print("\t metadata is [");
        // System.out.println("metadata is " + metadataSize);
        // System.out.println("index is " + index + ", dataList size is " + dataList.size());
        for (int i=0; i < metadataSize && index < dataList.size(); i++) {
            node.metadata[i] = dataList.get(index++);
            // System.out.print("" + node.metadata[i] + ",");
            sumValue += node.metadata[i];
        }
        // System.out.println("]");
        return node;
    }

    @Override
    public void doFileLogic() throws IOException {
        super.doFileLogic();
        dataList.add(Integer.parseInt(tempStr));
        // System.out.println("\n\n\n\n");
        Node root = resolve1();
        // System.out.println(root);
        System.out.println("First part result is :" + root.sum());
        System.out.println("Second part result is :" + root.getNodeValue());
    }

    public static void main(String[] args) {
        DayEightPart dep = new DayEightPart();
        try {
            dep.doFileLogic();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}