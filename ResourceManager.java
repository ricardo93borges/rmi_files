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
        for (Resource resource : resources) {
            this.resources.put(resource.getHash(), resource.getIp());
        }
    }

    public ArrayList<String> getResources() {
        ArrayList<String> resources = new ArrayList<String>();
        for (String key : this.resources.keySet()) {
            resources.add(key);
        }
        return resources;
    }

    public String getResourceLocation(String hash) {
        return this.resources.get(hash);
    }

    public void removeResources(String ip) {
        for (String key : this.resources.keySet()) {
            if (this.resources.get(key).equals(ip)) {
                this.resources.remove(key);
            }
        }
    }
}
