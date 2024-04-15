import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class KvizImpl extends UnicastRemoteObject implements Kviz {

  private int index;
  private int brojPoena;

  private ArrayList<Pitanje> pitanjaKviza;
  private ArrayList<String> odgovoriKviza;

  public KvizImpl() throws RemoteException {
    super();

    pitanjaKviza = new ArrayList<Pitanje>(3);
    odgovoriKviza = new ArrayList<String>(3);

    pitanjaKviza.add(new PitanjeImpl("1 + 1 = ?\n", "1", "2", "3"));
    odgovoriKviza.add("2");

    pitanjaKviza.add(new PitanjeImpl("2 * 3 = ?\n", "5", "2", "6"));
    odgovoriKviza.add("6");

    pitanjaKviza.add(new PitanjeImpl("Sta pije krava\n", "mleko", "rakiju", "vodu"));
    odgovoriKviza.add("vodu");

    index = -1;
  }

  @Override
  public void pocetak() throws RemoteException {
    index = 0;
    brojPoena = 0;
  }

  @Override
  public Pitanje vratiPitanje() throws RemoteException {
    return pitanjaKviza.get(index++);
  }

  @Override
  public void odgovori(String odg) throws RemoteException {

    PitanjeImpl pitanje = (PitanjeImpl)pitanjaKviza.get(index - 1);
    ArrayList<String> ponudjeniOdgovori = pitanje.getOdgovori();
    String odgovorPitanja = odgovoriKviza.get(index - 1);

    switch (odg) {
      case "a":
        brojPoena = ponudjeniOdgovori.get(0).equals(odgovorPitanja) ? brojPoena + 1 : brojPoena;
        break;
      case "b":
        brojPoena = ponudjeniOdgovori.get(1).equals(odgovorPitanja) ? brojPoena + 1 : brojPoena;
        break;
      case "c":
        brojPoena = ponudjeniOdgovori.get(2).equals(odgovorPitanja) ? brojPoena + 1 : brojPoena;
        break;
      default:
        break;
    }
  }

  @Override
  public int vratiBrojPoena() throws RemoteException {
    return brojPoena;
  }
}
