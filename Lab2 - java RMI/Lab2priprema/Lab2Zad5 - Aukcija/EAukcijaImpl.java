import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class EAukcijaImpl extends UnicastRemoteObject implements EAukcija {

  private HashMap<String, Eksponat> nizEksponata;

  public EAukcijaImpl() throws RemoteException {
    super();
    
    this.nizEksponata = new HashMap<>(5);

    nizEksponata.put("AA", new EksponatImpl("AA", "Vaza", 1450));
    nizEksponata.put("BB", new EksponatImpl("BB", "Slika", 12000));
    nizEksponata.put("CC", new EksponatImpl("CC", "Novcanica", 5500));
    nizEksponata.put("DD", new EksponatImpl("DD", "Sat", 500));
    nizEksponata.put("EE", new EksponatImpl("EE", "Upaljac", 20));

  }

  @Override
  public Eksponat vratiEksponat(String idEksponata) throws RemoteException {
    return nizEksponata.get(idEksponata);
  }
}
