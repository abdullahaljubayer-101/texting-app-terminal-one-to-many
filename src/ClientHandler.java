import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    private String clientUserName;

    public ClientHandler(Socket socket) {

        try {

            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName = bufferedReader.readLine();

            clientHandlers.add(this);

            broadcastMassage("SERVER: " + clientUserName + " has Entered the Chat!");

        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }

    }

    @Override
    public void run() {
        String massageFromClient;
        while (socket.isConnected()) {
            try {
                massageFromClient = bufferedReader.readLine();
                if (massageFromClient == null) {
                    closeEverything(socket, bufferedWriter, bufferedReader);
                    break;
                }
                broadcastMassage(massageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
            }
        }
    }
    public void broadcastMassage(String massageToSent) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUserName.equals(clientUserName)) {
                    clientHandler.bufferedWriter.write(massageToSent);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
            }
        }

    }

    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {

        broadcastMassage("SERVER: " + clientUserName + " has Left the Chat!");
        clientHandlers.remove(this);

        try {
            if (socket != null)
                socket.close();
            if (bufferedWriter != null)
                bufferedWriter.close();
            if (bufferedReader != null)
                bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
