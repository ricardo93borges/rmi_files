import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.OutputStream;

public class Client {

    private String ip;
    private String dir;
    private String connectLocation;
    private ArrayList<Resource> resources;
    private ResourceManagerInterface resourceManager;

    public Client(String host, String dir) {
        this.dir = dir;
        this.connectLocation = "//" + host + "/ResourceManager";
        this.setIp();
    }

    public void run() {
        try {
            System.out.println("Connecting to server at : " + this.connectLocation);
            this.resourceManager = (ResourceManagerInterface) Naming.lookup(connectLocation);
        } catch (Exception e) {
            System.out.println("AdditionClient failed: ");
            e.printStackTrace();
        }

        try {
            this.resources = this.getResources(dir);
            this.resourceManager.add(this.resources);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Runnable connectionHandler = new ConnectionHandler(this.dir);
            new Thread(connectionHandler).start();

            while(true) {
                //Get and select a resource
                ArrayList<String> resources = this.requestResources();
                String randomResource = this.selectRandomResource(resources);
                Resource resourceLocation = this.requestResourceLocation(randomResource);

                //Open connection
                Socket socket = new Socket(resourceLocation.getIp(), 3322);

                //Send resource name
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeUTF(resourceLocation.getName());
                dataOutputStream.flush();
                dataOutputStream.close();

                //Receive resource
                Scanner input = new Scanner(socket.getInputStream());
                while(input.hasNextLine()){
                    System.out.println(input.nextLine());
                }

                input.close();
                socket.close();

                TimeUnit.SECONDS.sleep(5);
            }

        } catch (Exception e) {
            System.out.println("AdditionClient failed: ");
            e.printStackTrace();
        }       
    }

    public ArrayList<Resource> getResources(String dir) throws IOException, NoSuchAlgorithmException {
        File folder = new File(dir);
        File[] files = folder.listFiles();
        ArrayList<Resource> resources = new ArrayList<Resource>();

        for (File file : files) {
            if (file.isFile()) {
                Path path = Paths.get(dir + "/" + file.getName());
                Charset charset = Charset.forName("ISO-8859-1");
                String content = Files.readAllLines(path, charset).stream().collect(Collectors.joining(""));
                String hash = new String(this.hash(content), "UTF-8");
                resources.add(new Resource(hash, this.ip, file.getName()));
            }
        }

        return resources;
    }

    public ArrayList<String> requestResources() throws RemoteException {
        return this.resourceManager.getResources();
    }

    public Resource requestResourceLocation(String hash) throws RemoteException {
        return this.resourceManager.getResourceLocation(hash);
    }

    public String selectRandomResource(ArrayList<String> resources) {
        Random random = new Random();
        int index = random.nextInt(resources.size());
        return resources.get(index);
    }

    public byte[] hash(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte hash[] = md.digest(str.getBytes("UTF-8"));
        return hash;
    }

    public void setIp() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            this.ip = ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
