import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ResourceManager extends UnicastRemoteObject implements ResourceManagerInterface {
    private static final long serialVersionUID = 1L;

    private HashMap<String, String> resources;

    public ResourceManager() throws RemoteException {
        this.resources = new HashMap<String, String>();
    }

    public void add(ArrayList<Resource> resources) {
        for(Resource resource: resources){
            this.resources.put(resource.getHash(), resource.getIp());
        }
    }

    public HashMap<String, String> getResources() {
        return this.resources;
    }
}
