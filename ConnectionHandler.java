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
            ServerSocket server = new ServerSocket(3322);
            System.out.println("> [ServerSocket] Server started on "+server.getInetAddress()+":"+server.getLocalPort());
             
            while(true) {
                Socket client = server.accept();
                RequestHandler requestHandler = new RequestHandler(client, dir);
                new Thread(requestHandler).start();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}