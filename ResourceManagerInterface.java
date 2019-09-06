import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ResourceManagerInterface extends Remote {
    public void add(ArrayList<Resource> resources) throws RemoteException;
    public HashMap<String, String> getResources() throws RemoteException;
}
