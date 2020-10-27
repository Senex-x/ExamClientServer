public class Task {
    public static final String name = "Vadim Polyakov";

    public static double[][] points;
    public static int[] classes;
    public static double x;
    public static double y;

    public static double[][] normalized;
    public static double[] proximities;
    public static int resultClass;

    public static void main(String[] args) {
        Client.start();
    }


    public static void firstTask() {
        normalized = new double[points.length][2];
        proximities = new double[3];
        int minX = -1;
        int maxX = 1;
        int minY = -10;
        int maxY = 10;
        for (int i = 0; i < points.length; i++) {
            normalized[i][0] = (points[i][0] - minX) / (maxX - minX);
            normalized[i][1] = (points[i][1] - minY) / (maxY - minY);
        }
        x = (x - minX) / (maxX - minX);
        y = (y - minY) / (maxY - minY);
    }

    public static void secondTask() {
        for (int i = 0; i < normalized.length; i++) {
            if (classes[i] == 0) {
                proximities[0] += 1 / distance(normalized[i][0], normalized[i][1]);
            }
            if (classes[i] == 1) {
                proximities[1] += 1 / distance(normalized[i][0], normalized[i][1]);
            }
            if (classes[i] == 2) {
                proximities[2] += 1 / distance(normalized[i][0], normalized[i][1]);
            }
        }
    }

    public static void thirdTask() {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            if(proximities[i] > max) {
                max = proximities[i];
                resultClass = i;
            }
        }
    }

    private static double distance(double pointX, double pointY) {
        return Math.sqrt(Math.pow(x - pointX, 2) + Math.pow(y - pointY, 2));
    }
}
