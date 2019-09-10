import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ResourceManagerInterface extends Remote {
    public void add(ArrayList<Resource> resources) throws RemoteException;

    public ArrayList<String> getResources(String excludeIp) throws RemoteException;

    public Resource getResourceLocation(String hash) throws RemoteException;
}
