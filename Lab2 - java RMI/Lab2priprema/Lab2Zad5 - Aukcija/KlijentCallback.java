import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KlijentCallback extends Remote {
    public void obavestiKlijente(int cena) throws RemoteException;
}