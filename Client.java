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
import java.util.stream.Collectors;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
            /**
                TODO (main thread)
                    while true 
                        request from server files list 
                        request from server random file location
                        request (via socket) from a client random file and write them                     
            */
            /**
                TODO (another thread)
                    accepts requests from clients for random files
                    handle this requests in another threads
            */
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
                resources.add(new Resource(hash, this.ip));
            }
        }

        return resources;
    }

    public ArrayList<String> requestResources() throws RemoteException {
        return this.resourceManager.getResources();
    }

    public String requestResourceLocation(String hash) throws RemoteException {
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
