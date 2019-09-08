import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

    private String dir;

    public ConnectionHandler(String dir){
        this.dir = dir;
    }

    @Override
    public void run() {
        try {
            System.out.println("ConnectionHandler thread : " + Thread.currentThread().getName());
            
            ServerSocket server = new ServerSocket(3322);                       
            System.out.println("Server started on port 3322");
             
            Socket client = server.accept();

            RequestHandler requestHandler = new RequestHandler(client, dir);
            new Thread(requestHandler).start();
            
            server.close();
             
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}