import java.rmi.Naming;
import java.util.Scanner;

public class Klijent {
  
  private Operater operater;

  public Klijent(String objectName) {
    try {
      operater = (Operater)Naming.lookup(objectName);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public void run() {
    Scanner scanner = new Scanner(System.in);
    String brTelefona;
    int zaSlanje;
    Korisnik korisnik;
    Stanje stanjeRacuna;

    
    loop: while(true) {
      try {
        printOptions();
        String option = scanner.nextLine();

        switch (option) {
          case "a":
            brTelefona = printGetPhone(scanner);
            System.out.println("Unesite broj dodatnih minuta:");
            zaSlanje = readNextInt(scanner);
            korisnik = getKorisnik(brTelefona);
            korisnik.uplatiMinute(zaSlanje);
            break;
          case "b":
            brTelefona = printGetPhone(scanner);
            System.out.println("Unesite broj dodatnih poruka:");
            zaSlanje = readNextInt(scanner);
            korisnik = getKorisnik(brTelefona);
            korisnik.uplatiPoruke(zaSlanje);
            break;
          case "c":
            brTelefona = printGetPhone(scanner);
            System.out.println("Unesite broj dodatnog interneta:");
            zaSlanje = readNextInt(scanner);
            korisnik = getKorisnik(brTelefona);
            korisnik.uplatiInternet(zaSlanje);
            break;
          case "d":
            brTelefona = printGetPhone(scanner);
            korisnik = getKorisnik(brTelefona);
            stanjeRacuna = korisnik.vratiStanje();
            System.out.println("Vase trenutno stanje je:");
            System.out.println("Preostali broj minuta: " + stanjeRacuna.vratiMinute());
            System.out.println("Preostali broj poruka: " + stanjeRacuna.vratiPoruke());
            System.out.println("Preostali internet sadrzaj: " + stanjeRacuna.vratiInternet());
            System.out.println("Vas racun iznosi: " + stanjeRacuna.vratiRacun());
            break;
          case "e":
            break loop;
          default:
            System.out.println("Nevalidna opcija!");
            break;
        }
      } catch (NullPointerException e) {
        System.out.println("Korisnik ne postoji!");
      } catch (Exception e) {
        System.out.println(e.toString());
      } 
    }
    scanner.close();
  }

  private void printOptions() {
    System.out.println("Dobrodosli u korisnicki servis mobilnog operatera. Za nastavak izaberite opciju:");
    System.out.println("a) Uplata Minuta");
    System.out.println("b) Uplata Poruka");
    System.out.println("c) Uplata Interneta");
    System.out.println("d) Provera stanja");
    System.out.println("e) Kraj");
  }

  private int readNextInt(Scanner scanner) {
    int toReturn = scanner.nextInt();
    scanner.nextLine();
    return toReturn;
  }

  private Korisnik getKorisnik(String brTelefona) {
    Korisnik toReturn;
    try {
      toReturn = operater.vratiKorisnika(brTelefona);
    } catch (Exception e) {
      // TODO: handle exception
      toReturn = null;
      System.out.println(e.toString());
    }
    return toReturn;
  }

  private String printGetPhone(Scanner scanner) {
    System.out.println("Unesite broj telefona korisnika:");
    return scanner.nextLine();
  }
  public static void main(String args[]) {
    String objectName = args[0];
    new Klijent(objectName).run();

  }
}
