import com.google.gson.Gson;
// import org.json.simple.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

class Client {
    private static final String HOSTNAME_REMOTE = "157.245.129.238";
    private static final int PORT_REMOTE = 11000;

    private static final String RESULT_AC1 = "\nVERDICT: FIRST ACCEPTED";
    private static final String RESULT_AC2 = "\nVERDICT: SECOND ACCEPTED";
    private static final String RESULT_AC3 = "\nVERDICT: THIRD ACCEPTED";
    private static final String RESULT_WA = "\nVERDICT: NONE ACCEPTED";

    private static InputStream inputStream;
    private static OutputStream outputStream;

    static void start() {
        try {
            // basic initialisation
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(HOSTNAME_REMOTE, PORT_REMOTE), 2000);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // sending start request to server for it to send respond data
            sendStartRequest();

            // receiving and parsing json response file to instance of ResponseItem
            ResponseItem responseItem = getServerResponse();

            // setting received data to the Task class
            Task.points = responseItem.getPoints();
            Task.classes = responseItem.getClasses();
            Task.x = responseItem.getX();
            Task.y = responseItem.getY();

            // executing Task methods and sending results to server
            sendResults();

            // receiving and parsing json response file to get verdict from server
            responseItem = getServerResponse();
            switch (responseItem.getType()) {
                case 0:
                    // all wrong
                    System.out.println(RESULT_WA);
                    break;
                case 1:
                    // 1 accepted
                    System.out.println(RESULT_AC1);
                    break;
                case 2:
                    // 2 accepted
                    System.out.println(RESULT_AC2);
                    break;
                case 3:
                    // 1, 2 accepted
                    System.out.println(RESULT_AC1);
                    System.out.println(RESULT_AC2);
                    break;
                case 4:
                    // 3 accepted
                    System.out.println(RESULT_AC3);
                    break;
                case 5:
                    // 1, 3 accepted
                    System.out.println(RESULT_AC1);
                    System.out.println(RESULT_AC3);
                    break;
                case 6:
                    // 2, 3 accepted
                    System.out.println(RESULT_AC2);
                    System.out.println(RESULT_AC3);
                    break;
                case 7:
                    // 1, 2, 3 accepted
                    System.out.println(RESULT_AC1);
                    System.out.println(RESULT_AC2);
                    System.out.println(RESULT_AC3);
                    break;

            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendStartRequest() throws IOException {
        // making startRequest json file
        Message message = new Message();
        message.Name = Task.name;
        message.MessageType = 0;

        String json = new Gson().toJson(message);

        display("Sent request message: ", json);
        // sending startRequest json file to server
        outputStream.write(json.getBytes());
    }

    private static ResponseItem getServerResponse() throws IOException {
        byte[] byteData = new byte[4096];
        // receiving json file and writing it to byte array
        inputStream.read(byteData);
        // making string from received byte array
        String response = new String(byteData);
        // cleaning string from service characters which are ruining parsing
        response = response.replaceAll("\\p{C}", "");
        display("Received message: ", response);
        // parsing json file to ResponseItem instance
        return new Gson().fromJson(response, ResponseItem.class);
    }

    private static void sendResults() throws IOException {
        // making results json file

        Task.firstTask();
        Task.secondTask();
        Task.thirdTask();

        Message message = new Message();
        message.Name = Task.name;
        message.MessageType = 1;
        message.Normalized = Task.normalized;
        message.Proximities = Task.proximities;
        message.ResultClass = Task.resultClass;

        String json = new Gson().toJson(message);

        display("Sent result message: ", json);
        // sending results json file to server
        outputStream.write(json.getBytes());
    }

    // logs can be easily disabled by changing this method
    private static <T> void display(String message, T object) {
        System.out.println(message + object);
    }

    private static String getString(double[] object) {
        String result = "";
        for(double x:object) {
            result += Double.toString(x);
        }
        return result;
    }
}

