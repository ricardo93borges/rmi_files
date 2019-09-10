import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager extends UnicastRemoteObject implements ResourceManagerInterface {
    private static final long serialVersionUID = 1L;

    private HashMap<String, Resource> resources;

    public ResourceManager() throws RemoteException {        
        this.resources = new HashMap<String, Resource>();
    }

    public void add(ArrayList<Resource> resources) {
        System.out.println("> Received resources");
        for (Resource resource : resources) {
            this.resources.put(resource.getHash(), resource);
        }
        this.printResources();
    }

    public ArrayList<String> getResources(String excludeIp) {
        ArrayList<String> resources = new ArrayList<String>();
        for (String key : this.resources.keySet()) {
            if(!this.resources.get(key).getIp().equals(excludeIp)){
                resources.add(key);
            }
        }
        return resources;
    }

    public Resource getResourceLocation(String hash) {
        return this.resources.get(hash);
    }

    public void removeResources(String ip) {
        for (String key : this.resources.keySet()) {
            if (this.resources.get(key).equals(ip)) {
                this.resources.remove(key);
            }
        }
    }

    public void printResources() {
        System.out.println("> resources list:");
		for (Map.Entry<String, Resource> entry : this.resources.entrySet()) {
		    System.out.println(entry.getValue().getIp() + ", " + entry.getValue().getName() );
        }
    }
}
