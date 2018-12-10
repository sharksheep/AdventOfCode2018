import java.io.IOException;
import java.util.ArrayList;

public class DayTenPart extends FileReadContext {

    public ArrayList<Point> dataList = new ArrayList<>();

    class Point {
        int x;
        int y;
        int vx;
        int vy;

        public Point(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public void doSpeed() {
            x = x + vx;
            y = y + vy;
        }

        @Override
        public boolean equals(Object obj) {
            return this.x == ((Point)obj).x && this.y == ((Point)obj).y;
        }

        @Override
        public String toString() {
            return "[x:"+x+",y:"+y+"]";
        }

    }

    @Override
    public String getFileName() {
        return "day1001.txt";
    }

    @Override
    public String readPuzzleLine(String line) {
        String p1 = line.substring(line.indexOf("position=<") + 10, line.indexOf("> velocity"));
        String speed = line.substring(line.indexOf("velocity=<") + 10, line.lastIndexOf(">"));
        // System.out.println("Point:" + point+",speed:" + speed);
        String[] p2 = p1.replace(" ", "").split(",");
        String[] s2 = speed.replace(" ", "").split(",");
        try {
            Point point = new Point(Integer.parseInt(p2[0]), Integer.parseInt(p2[1]), Integer.parseInt(s2[0]), Integer.parseInt(s2[1]));
            dataList.add(point);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isPrintWorld() {
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE, minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (Point var : dataList) {
            maxX = Math.max(var.x, maxX);
            minX = Math.min(var.x, minX);
            maxY = Math.max(var.y, maxY);
            minY = Math.min(var.y, minY);
        }

        int sp = maxY - minY;
        System.out.println("mxY - miY=" + sp);
        if (sp >= 10) {
        //     if (sp < 50) {
        //     for (int i = minY; i <= maxY; i++) {
                
        //         for (int j = minX; j <= maxX; j++) {
        //             Point point = new Point(j, i, 0, 0);
        //             if (dataList.contains(point)) {
        //                 System.out.print("#");
        //             } else {
        //                 System.out.print(".");
        //             }
        //         }
        //         System.out.println();
        //     }
        //     System.out.println("\n\n\n\n\n\n\n");
        // }
            return false;
        } else {
            for (int i = minY; i <= maxY; i++) {
                
                for (int j = minX; j <= maxX; j++) {
                    Point point = new Point(j, i, 0, 0);
                    if (dataList.contains(point)) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println();
            }
            System.out.println("\n\n\n\n\n");
            return true;
        }

    }

    public void resolve1() {
        int max = Integer.MAX_VALUE;
        for (int i = 1; i < max; i++) {
            // System.out.println(i);
            for (Point var : dataList) {
                // System.out.print(var + "\t");
                var.doSpeed();
                // System.out.println(var);
            }
            if (isPrintWorld()) {
                System.out.println("Part 2 result is:" + i);
                break;
            }
        }
    }

    @Override
    public void doFileLogic() throws IOException {
        super.doFileLogic();
        resolve1();
    }

    public static void main(String[] args) {
        DayTenPart dtp = new DayTenPart();
        try {
            long startTimes = System.nanoTime();
            dtp.doFileLogic();
            long costTimes = System.nanoTime() - startTimes;
            System.out.println("Cost time is:" + costTimes + " ns");
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

}