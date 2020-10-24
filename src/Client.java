import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
            // basic inits
            init();
            JSONObject startRequest = createStartRequestJson();
            System.out.println(startRequest.toString());

            // sending startRequestJson file to server
            outputStream.write(startRequest.toString().getBytes());

            // receiving and displaying json response file

            byte[] b = new byte[64];
            Gson gson = new Gson();
            inputStream.read(b);
            String responseS = new String(b);
            responseS = responseS.replaceAll("\\p{C}", "");
            System.out.println(responseS);
            ResponseItem responseItem = gson.fromJson(responseS, ResponseItem.class);
            System.out.println(responseItem.toString());



            // sending student's method's outputs to server


            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void init() throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(HOSTNAME_REMOTE, PORT_REMOTE), 2000);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    private static JSONObject createStartRequestJson() {
        JSONObject startRequest = new JSONObject();
        startRequest.put(TYPE, 0);
        startRequest.put(OUT1, -1);
        startRequest.put(OUT2, -1);
        return startRequest;
    }

    private static ResponseItem getServerResponse() throws IOException {
        ResponseItem responseItem = new ResponseItem();
        BufferedReader inputData = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder content = new StringBuilder();
        int value;
        while ((value = inputData.read()) != -1) {
            content.append((char) value);
            if ((char) value == ':') {
            }
            if ((char) value == '}') break;
        }
        inputData.close();
        System.out.println(content);
        return responseItem;
    }

    private static void displayInput(BufferedReader in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = bufferedReader.readLine()) != null) {
            response.append(responseLine);
        }
        System.out.println(response.toString());
    }

    private static JSONObject getResults() {
        JSONObject results = new JSONObject();
        results.put(TYPE, 1);
        results.put(OUT1, Task.ans1); // getting result via field
        results.put(OUT2, Task.secondTask()); // getting result directly from the method
        return results;
    }

    static private int a = 0;
    private static final String TAG = "Debug log message ";

    private static void log() {
        System.out.println(TAG + a++);
    }

    private static void log(int id) {
        System.out.println(TAG + id);
    }
}

