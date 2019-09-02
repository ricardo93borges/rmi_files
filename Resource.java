import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Resource extends UnicastRemoteObject implements ResourceInterface {
    private static final long serialVersionUID = 1L;

    public ArrayList<Peer> resources;

    public Resource() throws RemoteException {
        this.resources = new ArrayList<String>();
    }

    public void add(Peer peer) {
        this.resources.add(peer);
        System.out.println(this.resources.toString());
    }
}
