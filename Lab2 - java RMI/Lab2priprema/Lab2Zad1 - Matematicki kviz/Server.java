import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

  public Server(String objectName) {
    try {
      LocateRegistry.createRegistry(1099);

      Kviz kviz = new KvizImpl();
      Naming.rebind("rmi://localhost:1099/" + objectName, kviz);
    } catch(Exception e) {
      System.out.println(e.toString());
    }
  }

  public static void main(String args[]) {
    String objectName = args[0];

    new Server(objectName);

    System.out.println("Server je startovan.");
    
    try {
      System.in.read();
    } catch(Exception e) {
      System.out.println(e.toString());
    }
  }
}