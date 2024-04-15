import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Kviz extends Remote {

  public void pocetak() throws RemoteException;
  public Pitanje vratiPitanje() throws RemoteException;
  void odgovori(String odg) throws RemoteException;
  int vratiBrojPoena() throws RemoteException;
  
}