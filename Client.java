import java.io.File;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client {

    private String host;
    private String dir;
    private String connectLocation;

    public Client(String host, String dir) {
        this.host = host;
        this.dir = dir;
        this.connectLocation = "//" + host + "/ResourceManager";
        this.getFiles();
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
            Peer peer = new Peer("1.0.0.1", new ArrayList<Resource>());
            resourceManager.add(peer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getFiles() {
        File folder = new File("/tmp/");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }
    }
}
