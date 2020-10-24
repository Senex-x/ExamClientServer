public class Task {
    static int inp1 = 0;
    static int inp2 = 0;

    static int ans1;
    static int ans2;

    public static void main(String[] args) {
        Client.start();
    }

    static int firstTask() {
        ans1 = inp1 + inp2;
        return ans1;
    }

    static int secondTask() {
        ans2 = inp1 * inp2;
        return ans2;
    }
}
