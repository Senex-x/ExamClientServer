import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    static private final String HOSTNAME_LOCAL = "127.0.0.1";
    static private final int PORT_LOCAL = 6000;
    static private final String HOSTNAME_REMOTE = "157.245.129.238";
    static private final int PORT_REMOTE = 11000;
    static private final String HOSTNAME_TEST = "85.26.233.109";

    static private final String TYPE = "MessageType";
    static private final String OUT1 = "Sum";
    static private final String OUT2 = "Multiply";

    static private Socket socket;
    static private InputStream inputStream;
    static private OutputStream outputStream;

    static boolean end;

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

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static JSONObject createResponseJson(int type, int sum, int multiply) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TYPE, type);
        jsonObject.put(OUT1, sum);
        jsonObject.put(OUT2, multiply);
        return jsonObject;
    }


    private static void sendResults() throws IOException {
        JSONObject results = createResponseJson(1, Task.firstTask(), Task.secondTask());
        System.out.println("Sent result message: " + results);
        outputStream.write(results.toString().getBytes());
    }

    private static void sendStartRequest() throws IOException {
        // making startRequest json file
        JSONObject startRequest = createResponseJson(0, -1, -1);
        System.out.println("Sent request message: " + startRequest.toString());
        // sending startRequest json file to server
        outputStream.write(startRequest.toString().getBytes());
    }

    private static ResponseItem getServerResponse() throws IOException {
        byte[] byteData = new byte[64];
        // receiving json file as byte array
        inputStream.read(byteData);
        // making string from received byte array
        String response = new String(byteData);
        // cleaning string from service characters which are ruining parsing
        response = response.replaceAll("\\p{C}", "");
        System.out.println("Received message: " + response);
        // parsing json file to ResponseItem instance
        ResponseItem responseItem = new Gson().fromJson(response, ResponseItem.class);
        return responseItem;
    }

    private static final String TAG = "Debug log message ";

    private static void log(int id) {
        System.out.println(TAG + id);
    }
}

