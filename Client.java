import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server.");
            System.out.println(in.readLine()); 

            String userMessage;
            while (true) {
                System.out.print("You: ");
                userMessage = userInput.readLine();
                out.println(userMessage);

                if (userMessage.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.println(in.readLine()); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
