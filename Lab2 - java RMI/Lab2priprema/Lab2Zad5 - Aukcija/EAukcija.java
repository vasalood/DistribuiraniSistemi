import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EAukcija extends Remote {
  public Eksponat vratiEksponat(String idEksponata) throws RemoteException;
}
