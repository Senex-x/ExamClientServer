import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    private static final String HOSTNAME_LOCAL = "127.0.0.1";
    private static final int PORT_LOCAL = 6000;
    private static final String HOSTNAME_REMOTE = "157.245.129.238";
    private static final int PORT_REMOTE = 11000;

    private static final String TYPE = "MessageType";
    private static final String OUT1 = "Sum";
    private static final String OUT2 = "Multiply";

    private static final String RESULT_AC = "VERDICT: ACCEPTED";
    private static final String RESULT_WA = "VERDICT: WRONG ANSWER";
    private static final String RESULT_RE = "VERDICT: RUNTIME ERROR";

    private static Socket socket;
    private static InputStream inputStream;
    private static OutputStream outputStream;

    public static void main(String[] args) {
        try {
            // basic initialisation
            socket = new Socket();
            socket.connect(new InetSocketAddress(HOSTNAME_REMOTE, PORT_REMOTE), 2000);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // sending start request to server for it to respond me
            sendStartRequest();

            // receiving and parsing json response file to instance of ResponseItem
            ResponseItem responseItem = getServerResponse();
            Task.inp1 = responseItem.getX();
            Task.inp2 = responseItem.getY();

            // sending student's method's outputs to server
            sendResults();

            // receiving verdict either answer is true or not
            responseItem = getServerResponse();
            switch (responseItem.getMessageType()) {
                case 1:
                    System.out.println(RESULT_AC);
                    break;
                case 2:
                    System.out.println(RESULT_WA);
                    break;
                case 3:
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
        JSONObject startRequest = createResponseJson(0, -1, -1);
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
        ResponseItem responseItem = new Gson().fromJson(response, ResponseItem.class);
        return responseItem;
    }

    private static void sendResults() throws IOException {
        JSONObject results = createResponseJson(1, Task.firstTask(), Task.secondTask());
        display("Sent result message: ", results);
        outputStream.write(results.toString().getBytes());
    }

    private static JSONObject createResponseJson(int type, int sum, int multiply) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TYPE, type);
        jsonObject.put(OUT1, sum);
        jsonObject.put(OUT2, multiply);
        return jsonObject;
    }

    private static <T> void display(String message, T object) {
        System.out.println(message + object);
    }
}

