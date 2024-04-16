import java.io.Serializable;

public class KlijentAukcije implements Serializable {

  private String idKlijenta;
  private String imeKlijenta;
  private String prezimeKlijenta;

  public KlijentAukcije(String klijentAukcijeId, String ime, String prezime) {
    idKlijenta = klijentAukcijeId;
    imeKlijenta = ime;
    prezimeKlijenta = prezime;
  }

  public String getIdKlijenta() {
    return idKlijenta;
  }

  public String getImeKlijenta() {
    return imeKlijenta;
  }

  public String getPrezimeKlijenta() {
    return prezimeKlijenta;
  }
}
