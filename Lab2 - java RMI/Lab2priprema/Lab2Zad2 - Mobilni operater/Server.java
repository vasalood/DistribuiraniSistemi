import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
  
  public Server(String objectName) {
    try {
      LocateRegistry.createRegistry(1099);
      Operater operater = new OperaterImpl();
      Naming.rebind(objectName, operater);
      System.out.println("Server je startovan...");
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.toString());
    }
  }
  public static void main(String args[]) {
    String objectName = args[0];
    new Server(objectName);
    try {
      System.in.read();
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.toString());
    }
  }
}