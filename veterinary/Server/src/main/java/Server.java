
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Server {

    private static ArrayList<ClientManager> users = new ArrayList<ClientManager>();


    public static void main(String[] args) throws IOException {
        try (ServerSocket socket = new ServerSocket(3000)) {
            while (true) {
                Logger log = Logger.getLogger(Server.class.getName());
                System.out.println(Instant.now() + " Waiting for client...");
                Socket clientSocket = socket.accept();
                ClientManager clientManager = new ClientManager(clientSocket); //socket nou pt fiecare client
                log.fine("Client logat");
                clientManager.start();
            }
        }
    }

    public static void addUserManager(ClientManager clientManager){
        users.add(clientManager);
    }

}


