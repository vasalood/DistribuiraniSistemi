import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class EksponatImpl extends UnicastRemoteObject implements Eksponat {

  private String idEksponata;
  private String nazivEksponata;
  private int cenaEksponata;

  private HashMap<String, KlijentCallback> callbacks;

  public EksponatImpl(String id, String naziv, int cena) throws RemoteException {
    super();

    idEksponata = id;
    nazivEksponata = naziv;
    cenaEksponata = cena;
    callbacks = new HashMap<String, KlijentCallback>();
  }

  @Override
  public synchronized void prijaviLicitaciju(KlijentAukcije ka, KlijentCallback callback) throws RemoteException {
    if(callbacks.containsKey(ka.getIdKlijenta())) return;
    callbacks.put(ka.getIdKlijenta(), callback);
    System.out.println("Pretplata klijenta id: " + ka.getIdKlijenta() + " ime: " + ka.getImeKlijenta() + " na licitaciju.");
  }

  @Override
  public synchronized void odustaniOdLicitacije(String klijentAukcijeId) throws RemoteException {
    if(!callbacks.containsKey(klijentAukcijeId)) return;
    System.out.println("Odustajanje klijenta " + klijentAukcijeId + " od licitacije.");
    callbacks.remove(klijentAukcijeId);
  }

  @Override
  public String vratiId() throws RemoteException {
    return idEksponata;
  }

  @Override
  public String vratiNaziv() throws RemoteException {
    return nazivEksponata;
  }

  @Override
  public int vratiCenu() throws RemoteException {
    return cenaEksponata;
  }

  @Override
  public void povecajCenu(int iznos, String idKlijenta) throws RemoteException {
    if(iznos > 0) cenaEksponata += iznos;
    
    for (Map.Entry<String, KlijentCallback> entry : callbacks.entrySet()) {
      if(!idKlijenta.equals(entry.getKey())) {
        KlijentCallback cb = entry.getValue();	
        cb.obavestiKlijente(cenaEksponata);		
      }
    } 
  }
}
