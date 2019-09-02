import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ResourceInterface extends Remote {
    public void add(Peer peer) throws RemoteException;
}
