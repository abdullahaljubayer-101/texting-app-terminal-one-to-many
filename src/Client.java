import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    Socket socket;

    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    String userName;

    public Client(Socket socket, String userName) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.userName = userName;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void sentMassage() {
        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);

            while (socket.isConnected()) {
                String massageToSent = scanner.nextLine();
                bufferedWriter.write(userName + ": " + massageToSent);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }

    public void listenToMassage() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String massageFromGroupChat;

                try {
                    while (socket.isConnected()) {

                        massageFromGroupChat = bufferedReader.readLine();
                        System.out.println(massageFromGroupChat);

                    }
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }

            }
        }).start();

    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

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

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Your UserName: ");

        String userName = scanner.nextLine();

        Socket socket = new Socket("localhost", 1234);

        Client client = new Client(socket, userName);
        client.listenToMassage();
        client.sentMassage();

    }

}
