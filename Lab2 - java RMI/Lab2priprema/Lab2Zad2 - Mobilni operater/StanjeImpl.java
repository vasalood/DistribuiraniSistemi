import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StanjeImpl extends UnicastRemoteObject implements Stanje {

  private int minuti;
  private int poruke;
  private int internet;
  private float racun;

  public StanjeImpl(int minuti, int poruke, int internet, float racun) throws RemoteException {
    super();
    this.minuti = minuti;
    this.poruke = poruke;
    this.internet = internet;
    this.racun = racun;
  }

  @Override
  public int vratiMinute() throws RemoteException {
    return minuti;
  }

  @Override
  public int vratiPoruke() throws RemoteException {
    return poruke;
  }

  @Override
  public int vratiInternet() throws RemoteException {
    return internet;
  }

  @Override
  public float vratiRacun() throws RemoteException {
    return racun;
  }
  
  public void setMinuti(int minuti, int minutiTarifa) {
    this.minuti += minuti;
    this.racun += minuti*minutiTarifa;
  }
  
  public void setPoruke(int poruke, int porukeTarifa) {
    this.poruke += poruke;
    this.racun += poruke * porukeTarifa;
  }

  public void setInternet(int internet, int internetTarifa) {
    this.internet += internet;
    this.racun += internet * internetTarifa;
  }
}