import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ResourceManager extends UnicastRemoteObject implements ResourceManagerInterface {
    private static final long serialVersionUID = 1L;

    public ArrayList<Peer> resources;

    public ResourceManager() throws RemoteException {
        this.resources = new ArrayList<Peer>();
    }

    public void add(Peer peer) {
        this.resources.add(peer);
        System.out.println(this.resources.toString());
        System.out.println(peer.getIp());
    }
}
