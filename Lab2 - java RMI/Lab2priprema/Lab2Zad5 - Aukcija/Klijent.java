import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Klijent {

  private EAukcija aukcija;
  private KlijentCallback callback;

  public Klijent(String objectName) {
    try {
      aukcija = (EAukcija)Naming.lookup(objectName);
      callback = new KlijentCallbackImpl();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }


  private void run() {
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Unesite Vas jedinstveni identifikator:");
      String idKlijenta = scanner.nextLine();
      System.out.println("Unesite Vase ime:");
      String imeKlijenta = scanner.nextLine();
      System.out.println("Unesite Vase prezime:");
      String prezimeKlijenta = scanner.nextLine();

      KlijentAukcije klijent = new KlijentAukcije(idKlijenta, imeKlijenta, prezimeKlijenta);

      System.out.println("Unesite identifikator eksponata od interesa:");
      String idEksponata = scanner.nextLine();

      Eksponat eksponat = aukcija.vratiEksponat(idEksponata);

      System.out.println("Naziv eksponata je: " + eksponat.vratiNaziv());

      boolean korReg = false;
      String unos;

      while(true) {
        System.out.println("Cena eksponata je: " + eksponat.vratiCenu());
        printOptions();

        unos = scanner.nextLine();

        if(unos.equals("a")) {

          if(!korReg) {
            eksponat.prijaviLicitaciju(klijent, callback);
            korReg = true;
          }

          System.out.println("Za koliko uvecavate iznos: ");
          int iznos = Integer.parseInt(scanner.nextLine());
          eksponat.povecajCenu(iznos, klijent.getIdKlijenta());
        } else if(unos.equals("b")) {
          eksponat.odustaniOdLicitacije(klijent.getIdKlijenta());
        } else {
          System.out.println("Nepostojeca opcija. Unesite a ili b!");
        }
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  private void printOptions() {
    System.out.println("Izaberite opciju:");
    System.out.println("a) Licitacija");
    System.out.println("b) Odustajanje");
  }

  private void cenaPromenjena(int cena) {
    System.out.println("!!! CENA JE PROMENJENA !!!");
    System.out.println("NOVA CENA JE " + cena + "$");
  }

  public class KlijentCallbackImpl extends UnicastRemoteObject implements KlijentCallback {

    public KlijentCallbackImpl() throws RemoteException {
      super();
    }

    @Override
    public void obavestiKlijente(int cena) throws RemoteException {
      cenaPromenjena(cena);
    }

  }

  public static void main(String args[]) {
    try {
      new Klijent(args[0]).run();
      System.in.read();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }
}
