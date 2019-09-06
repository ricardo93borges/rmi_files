import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Resource implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String hash;
    private String ip;

    public Resource(String hash, String ip) {        
        this.hash = hash;
        this.ip = ip;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
