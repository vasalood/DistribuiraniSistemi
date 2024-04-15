import java.rmi.Naming;
import java.util.Scanner;

public class Klijent {
  private Kviz kviz;

  public Klijent(String objectName) {
    try {
      kviz = (Kviz) Naming.lookup("rmi://localhost:1099/" + objectName);
    } catch(Exception e) {
      System.out.println(e.toString());
    }
  }

  public void run() {
    Scanner scanner = new Scanner(System.in);

    try {
      kviz.pocetak();

      System.out.println("Na pitanja odgovarajte sa: [a b c] " +  
                          "u zavisnosti od toga sta smatrate tacnim odgovorom");
      for(int i = 0; i < 3; i++) {
        
        System.out.println(kviz.vratiPitanje().vratiTekst());

        String odgovor = scanner.nextLine();

        kviz.odgovori(odgovor);
      }

      System.out.println("Ukupan broj poena je " + kviz.vratiBrojPoena());

    } catch(Exception e) {
      System.out.println(e.toString());
    } 

    scanner.close();
  }

  public static void main(String args[]) {
    String objectName = args[0];
    new Klijent(objectName).run();
  }

}
