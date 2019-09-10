import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
            InputStream input = this.client.getInputStream();
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(isr);
            String resourceName = br.readLine();

            System.out.println("resourceName: " + resourceName);
            
            //Get resource content
            Path path = Paths.get(this.dir + "/" + resourceName);
            Charset charset = Charset.forName("ISO-8859-1");
            String content = Files.readAllLines(path, charset).stream().collect(Collectors.joining(""));

            OutputStream os = this.client.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            String str = content + "\n";
            bw.write(str);
            bw.flush();

            //Send resource content
            input.close();
            this.client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}