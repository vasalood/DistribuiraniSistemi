import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class OperaterImpl extends UnicastRemoteObject implements Operater {

  private HashMap<String, Korisnik> listaKorisnika;

  public OperaterImpl() throws RemoteException {
    super();

    listaKorisnika = new HashMap<>(10);

    listaKorisnika.put("1234567", 
                      new KorisnikImpl("1234567", 100, 100, 2, 20, 10, 30));
    listaKorisnika.put("1234576", 
                      new KorisnikImpl("1234576", 50, 500, 1, 30, 5, 30));
    listaKorisnika.put("1234756", 
                      new KorisnikImpl("1234756", 200, 150, 6, 25, 15, 5));
    listaKorisnika.put("1237456", 
                      new KorisnikImpl("1237456", 1000, 500, 10, 10, 10, 10));
    listaKorisnika.put("1273456", 
                      new KorisnikImpl("1273456", 500, 500, 4, 20, 20, 20));
    listaKorisnika.put("1723456", 
                      new KorisnikImpl("1723456", 150, 150, 6, 20, 20, 30));
    listaKorisnika.put("7123456", 
                      new KorisnikImpl("7123456", 800, 400, 7, 10, 10, 30));
    listaKorisnika.put("1234657", 
                      new KorisnikImpl("1234657", 4000, 1000, 9, 40, 30, 30));
    listaKorisnika.put("1236457", 
                      new KorisnikImpl("1236457", 400, 400, 1, 20, 40, 10));
    listaKorisnika.put("1263457", 
                      new KorisnikImpl("1263457", 600, 300, 2, 30, 10, 35));

  }

  @Override
  public Korisnik vratiKorisnika(String broj) throws RemoteException {
    return listaKorisnika.get(broj);
  }
  
}
