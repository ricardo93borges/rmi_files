import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class RequestHandler implements Runnable {

    Socket client;
    String dir;

    public RequestHandler(Socket client, String dir) {
        this.client = client;
        this.dir = dir;
    }

    @Override
    public void run() {
        try{
            System.out.println("RequestHandler thread : " + Thread.currentThread().getName());
            System.out.println("client connected: "+this.client.getInetAddress().getHostAddress());

            //Get resource name
            Scanner input = new Scanner(this.client.getInputStream());
            String resourceName = input.nextLine();            

            System.out.println("resourceName: " + resourceName);
            
            //Get resource content
            Path path = Paths.get(this.dir + "/" + resourceName);
            Charset charset = Charset.forName("ISO-8859-1");
            String content = Files.readAllLines(path, charset).stream().collect(Collectors.joining(""));

            //Send resource content
            OutputStream outputStream = this.client.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(content);
            dataOutputStream.flush();
            
            dataOutputStream.close();
            input.close();
            this.client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}