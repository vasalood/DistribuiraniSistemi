import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class KorisnikImpl extends UnicastRemoteObject implements Korisnik {

  private StanjeImpl stanjeKorisnika;
  private int minutiTarifa;
  private int porukeTarifa;
  private int internetTarifa;

  public KorisnikImpl(String broj, 
                      int minuti, 
                      int poruke, 
                      int internet,
                      int minutiTarifa,
                      int porukeTarifa,
                      int internetTarifa) throws RemoteException {
    super();
    
    this.minutiTarifa = minutiTarifa;
    this.porukeTarifa = porukeTarifa;
    this.internetTarifa = internetTarifa;

    int racun = minuti * minutiTarifa + poruke * porukeTarifa + internet * internetTarifa;
    stanjeKorisnika = new StanjeImpl(minuti, poruke, internet, racun);
  }

  @Override
  public void uplatiMinute(int minuti) throws RemoteException {
    stanjeKorisnika.setMinuti(minuti, minutiTarifa);
  }

  @Override
  public void uplatiPoruke(int poruke) throws RemoteException {
    stanjeKorisnika.setPoruke(poruke, porukeTarifa);
  }

  @Override
  public void uplatiInternet(int internet) throws RemoteException {
    stanjeKorisnika.setInternet(internet, internetTarifa);
  }

  @Override
  public Stanje vratiStanje() throws RemoteException {
    return stanjeKorisnika;
  }
  
}
