import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class server {
    private static final int PORT = 1234;
    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Handle client in a new thread
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("Welcome to the server! Type 'exit' to disconnect.");

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) {
                    out.println("Goodbye!");
                    break;
                }
                System.out.println("Client [" + socket.getInetAddress() + "]: " + message);
                out.println("Server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("Client disconnected: " + socket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
