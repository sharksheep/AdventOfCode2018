import java.io.IOException;
import java.util.ArrayList;

public class DayThirdArea extends FileReadContext {
    public ArrayList<Position> dataList = new ArrayList<>();
    public int result1 = 0;

    class Position {
        // int index;
        int startX;
        int startY;
        int endX;
        int endY;

        public Position(int startX, int startY, int endX, int endY) {
            // this.index = index;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }

        public int getArea() {
            return (Math.abs(endX - startX) + 1) * (Math.abs(endY - startY) + 1);
        }

        @Override
        public String toString() {
            return "Position is [("+startX+","+startY+"),("+endX+","+endY+")]";
        }
    }

    /**
     * Get file from file path
     */
    @Override
    public String getFileName() {
        return "day301.txt";
    }

    @Override
    public String readPuzzleLine(String line) {
        String targetStr = line.substring(line.indexOf("@") + 1);
        String[] splitArray = targetStr.trim().split(":");
        String[] splitPosition1 = splitArray[0].trim().split(",");
        String[] splitPosition2 = splitArray[1].trim().split("x");
        Position position = new Position(Integer.parseInt(splitPosition1[0]),Integer.parseInt(splitPosition1[1]),
                                         Integer.parseInt(splitPosition1[0]) + Integer.parseInt(splitPosition2[0]), 
                                         Integer.parseInt(splitPosition1[1]) + Integer.parseInt(splitPosition2[1]));
        dataList.add(position);
        return line;
    }

    public void getResolveFirst() {
        int size = dataList.size();
        ArrayList<Position> valueList = extracted(dataList);
        // for (Position var : valueList) {
        //     result1 += var.getArea();
        // }
        result1 = valueList.size();
    }

	private ArrayList<Position> extracted(ArrayList<Position> datas) {
        ArrayList<Position> valueList = new ArrayList<>();
        int size = datas.size();
        for (int i = 0; i < size; i++) {
            Position start = datas.get(i);
            for (int j = i + 1; j < size; j++) {
                Position end = datas.get(j);
                Position valueP = isRectOverlap(start, end);
                if (valueP != null) {
                    System.out.println(start);
                    System.out.println(end);
                    System.out.println(valueP);
                    System.out.println("--------------");
                    valueList.add(valueP);
                } 
            }
        }
        return valueList;
    }

    public Position isRectOverlap(Position p1, Position p2) {
        int midX1 = (p1.startX + p1.endX) / 2;
        int midY1 = (p1.startY + p1.endY) / 2;
        int midX2 = (p2.startX + p2.endX) / 2;
        int midY2 = (p2.startY + p2.endY) / 2;
        // | Xb2+Xb1-Xa2-Xa1 | <= Xa2-Xa1 + Xb2-Xb1
        // | Yb2+Yb1-Ya2-Ya1 | <=Y a2-Ya1 + Yb2-Yb1
        boolean overlapX = Math.abs(midX2 - midX1) <= (p1.endX - p1.startX) / 2 + (p2.endX - p2.startX) / 2;
        boolean overlapY = Math.abs(midY2 - midY1) <= (p1.endY - p1.startY) / 2 + (p2.endY - p2.startY) / 2;
        if (overlapX && overlapY) {
            int valueX = Math.max(p1.startX, p2.startX);
            int valueY = Math.max(p1.startY, p2.startY);
            int valueEX = Math.min(p1.endX, p2.endX);
            int valueEY = Math.min(p1.endY, p2.endY);
            return new Position(valueX, valueY, valueEX, valueEY);
        }
        return null;
    }


    @Override
    public void doFileLogic() throws IOException {
        super.doFileLogic();
        getResolveFirst();
    }

    public static void main(String[] args) {
        try {
            long startTimes = System.nanoTime();
            DayThirdArea dayThirdArea = new DayThirdArea();
            dayThirdArea.doFileLogic();
            System.out.println(dayThirdArea.result1);
            long costTimes = System.nanoTime() - startTimes;
            System.out.println("Cost time is:" + costTimes + " ns");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}