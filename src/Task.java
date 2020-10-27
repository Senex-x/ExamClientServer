public class Task {
    static double[][] points;
    static double[] classes;
    static double x;
    static double y;

    static double[][] normalized;
    static double[] proximities;
    static int resultClass;

    public static void main(String[] args) {
        Client.start();
    }

    static double[][] firstTask() {
        normalized = new double[][] {new double[] {0}};
        return normalized;
    }

    static double[] secondTask() {
        proximities = new double[] {0};
        return proximities;
    }

    static int thirdTask() {
        resultClass = 0;
        return resultClass;
    }
}
