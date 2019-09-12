import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) throws RemoteException {
        if (args.length < 2) {
            System.out.println("Insufficient arguments");
            System.exit(1);
        }

        try {

            String type = args[0];
            String host = args[1];

            if (type.equals("server")) {
                Server server = new Server(host);
                server.run();
            } else if (type.equals("client")) {
                String ip = args[2];
                String dir = args[3];
                Client client = new Client(host, ip, dir);
                client.run();
            } else {
                System.out.println("Type unknown");
                System.exit(1);
            }

        } catch (RemoteException e) {
            System.out.println("RemoteException" + e.getMessage());
        }

    }
}