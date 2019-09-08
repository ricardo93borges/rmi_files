import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ResourceManagerInterface extends Remote {
    public void add(ArrayList<Resource> resources) throws RemoteException;

    public ArrayList<String> getResources() throws RemoteException;

    public String getResourceLocation(String hash) throws RemoteException;
}
