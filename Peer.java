import java.io.Serializable;
import java.util.ArrayList;

public class Peer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ip;
    private ArrayList<Resource> resources;

    public Peer(String ip, ArrayList<Resource> resources) {
        this.ip = ip;
        this.resources = resources;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ArrayList<Resource> getResources() {
        return this.resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }
}
