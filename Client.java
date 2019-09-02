import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Client {

    private String host;
    private String dir;
    private String connectLocation;

    public Client(String host, String dir) {
        this.host = host;
        this.dir = dir;
        this.connectLocation = "//" + host + "/Hello";

        System.out.println("Connecting to server at : " + this.connectLocation);
    }
}
