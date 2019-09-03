import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    private String host;

    public Server(String host) {
        this.host = host;
    }

    public void run() throws RemoteException {
        try {
            System.setProperty("java.rmi.server.hostname", this.host);
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created at " + this.host);
        } catch (RemoteException e) {
            System.out.println("java RMI registry already exists.");
        }

        try {
            Naming.rebind("ResourceManager", new ResourceManager());
            System.out.println("Server is ready.");
        } catch (Exception e) {
            System.out.println("Server failed: " + e.getMessage());
        }

    }
}
