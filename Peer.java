import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Peer {

    private String ip;
    private ArrayList<File> files;

    public Peer(String ip, ArrayList<File> files) {
        this.ip = ip;
        this.files = files;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ArrayList<File> getFiles() {
        return this.files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    public void addFile(File file) {
        this.files.add(file);
    }
}
