import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

  public Server(String objectName) {
    try {
      
      LocateRegistry.createRegistry(1099);
      EAukcija aukcija = new EAukcijaImpl();
      Naming.rebind(objectName, aukcija);
      System.out.println("Server je startovan...");
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public static void main(String args[]) {
    new Server(args[0]);
    try {
      System.in.read();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }
  
}