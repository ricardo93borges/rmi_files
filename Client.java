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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Client {

    private String host;
    private String ip;
    private String dir;
    private String connectLocation;
    private ArrayList<Resource> resources;

    public Client(String host, String dir) {
        this.host = host;
        this.dir = dir;
        this.connectLocation = "//" + host + "/ResourceManager";
        this.ip = "0.0.0.0";
    }

    public void run() {
        ResourceManagerInterface resourceManager = null;
        try {
            System.out.println("Connecting to server at : " + this.connectLocation);
            resourceManager = (ResourceManagerInterface) Naming.lookup(connectLocation);
        } catch (Exception e) {
            System.out.println("AdditionClient failed: ");
            e.printStackTrace();
        }

        try {
            this.resources = this.getResources(dir);
            resourceManager.add(this.resources);
            //HashMap<String, String> list = resourceManager.getResources();
            //System.out.println(list);
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
                Path path = Paths.get(dir+"/"+file.getName());
                Charset charset = Charset.forName("ISO-8859-1");
                String content = Files.readAllLines(path, charset).stream().collect(Collectors.joining(""));
                String hash = new String(this.hash(content), "UTF-8");
                resources.add(new Resource(hash, this.ip));
            }
        }

        return resources;
    }

    public byte[] hash(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{         
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte hash[] = md.digest(str.getBytes("UTF-8"));
        System.out.println(hash);
        return hash;
    }

}
