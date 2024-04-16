import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Eksponat extends Remote {
  //register
  public void prijaviLicitaciju(KlijentAukcije ka, KlijentCallback callback) throws RemoteException;
  //unregister
  public void odustaniOdLicitacije(String klijentAukcijeId) throws RemoteException;
  public String vratiId() throws RemoteException;
  public String vratiNaziv() throws RemoteException;
  public int vratiCenu() throws RemoteException;
  public void povecajCenu(int iznos, String idKlijenta) throws RemoteException;
}
