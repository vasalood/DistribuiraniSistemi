import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class PitanjeImpl extends UnicastRemoteObject implements Pitanje {

  private String tekstPitanja;
  private ArrayList<String> ponudjeniOdgovori;

  public PitanjeImpl(String tekst, String a, String b, String c) throws RemoteException {
    super();
    tekstPitanja = tekst;
    ponudjeniOdgovori = new ArrayList<String>(3);
    ponudjeniOdgovori.add(a);
    ponudjeniOdgovori.add(b);
    ponudjeniOdgovori.add(c);
  }

  @Override
  public String vratiTekst() throws RemoteException {

    String toReturn = tekstPitanja + "a) " 
                      + ponudjeniOdgovori.get(0) + " b) "
                      + ponudjeniOdgovori.get(1) + " c) "
                      + ponudjeniOdgovori.get(2);
    return toReturn;
  }

  public ArrayList<String> getOdgovori() {
    return ponudjeniOdgovori;
  }

}
