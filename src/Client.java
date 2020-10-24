import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

class Client {
    private static final String HOSTNAME_LOCAL = "127.0.0.1";
    private static final int PORT_LOCAL = 6000;
    private static final String HOSTNAME_REMOTE = "157.245.129.238";
    private static final int PORT_REMOTE = 11000;

    private static final String TYPE = "MessageType";
    private static final String OUT1 = "Sum";
    private static final String OUT2 = "Multiply";

    private static final String RESULT_AC = "\nVERDICT: ACCEPTED";
    private static final String RESULT_WA = "\nVERDICT: WRONG ANSWER";
    private static final String RESULT_RE = "\nVERDICT: RUNTIME ERROR";

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
            Task.inp1 = responseItem.getX();
            Task.inp2 = responseItem.getY();

            // executing Task methods and sending results to server
            sendResults();

            // receiving and parsing json response file to get verdict from server
            responseItem = getServerResponse();
            switch (responseItem.getMessageType()) {
                case 1:
                    // accepted
                    System.out.println(RESULT_AC);
                    break;
                case 2:
                    // wrong answer
                    System.out.println(RESULT_WA);
                    break;
                case 3:
                    // runtime error
                    System.out.println(RESULT_RE);
                    break;
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendStartRequest() throws IOException {
        // making startRequest json file
        JSONObject startRequest = createJsonResponse(0, -1, -1);
        display("Sent request message: ", startRequest.toString());
        // sending startRequest json file to server
        outputStream.write(startRequest.toString().getBytes());
    }

    private static ResponseItem getServerResponse() throws IOException {
        byte[] byteData = new byte[64];
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
        JSONObject results = createJsonResponse(1, Task.firstTask(), Task.secondTask());
        display("Sent result message: ", results);
        // sending results json file to server
        outputStream.write(results.toString().getBytes());
    }

    private static JSONObject createJsonResponse(int type, int sum, int multiply) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TYPE, type);
        jsonObject.put(OUT1, sum);
        jsonObject.put(OUT2, multiply);
        return jsonObject;
    }

    // logs can be easily disabled by changing this method
    private static <T> void display(String message, T object) {
        System.out.println(message + object);
    }
}

