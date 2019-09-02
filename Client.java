import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Client {

    private String host;
    private String dir;
    private String connectLocation;

    public Client(String host, String dir) {
        this.host = host;
        this.dir = dir;
        this.connectLocation = "//" + host + "/Resource";
    }

    public void run() {
        ResourceInterface resource = null;
        try {
            System.out.println("Connecting to server at : " + this.connectLocation);
            resource = (ResourceInterface) Naming.lookup(connectLocation);
        } catch (Exception e) {
            System.out.println("AdditionClient failed: ");
            e.printStackTrace();
        }

        try {
            Peer peer = new Peer("1.0.0.1", new ArrayList<File>());
            resource.add(peer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
