using Grpc.Core;

namespace GrpcServer.Services
{
    public class KorisnikService : ServiceKorisnik.ServiceKorisnikBase
    {
        private readonly ILogger<KorisnikService> _logger;
        public KorisnikService(ILogger<KorisnikService> logger)
        {
            _logger = logger;
        }
        public override Task<Poruka> DodajKorisnika(Korisnik korisnik, ServerCallContext context)
        {
            Korisnik? k = Korisnici.Instanca().ListaKorisnika
                                              .FirstOrDefault(kor => kor.Id == korisnik.Id);
            if (k != null) 
            {
                return Task.FromResult(new Poruka { Tekst = "Korisnik sa unetim Id-jem vec postoji!" });
            }

            Korisnici.Instanca().ListaKorisnika.Add(korisnik);

            return Task.FromResult(new Poruka { Tekst = $"Korisnik {korisnik.Ime} " +
                                                    $"sa Id-jem {korisnik.Id}" +
                                                    $" je uspesno dodat!" });
        }
        public override Task<Korisnik> PreuzmiKorisnika(Id id, ServerCallContext context)
        {
            Korisnik? korisnik = Korisnici.Instanca().ListaKorisnika.FirstOrDefault(korisnik => korisnik.Id == id.Id_);

            return korisnik != null ? Task.FromResult(korisnik) : Task.FromResult(new Korisnik());
        }
        public override Task<Poruka> ObrisiKorisnika(Id id, ServerCallContext context)
        {
            var listaKorisnika = Korisnici.Instanca().ListaKorisnika;
            Korisnik? korisnik = listaKorisnika.FirstOrDefault(korisnik => korisnik.Id == id.Id_);

            if (korisnik != null)
            {
                listaKorisnika.Remove(korisnik);
                return Task.FromResult(new Poruka { Tekst = "Korisnik je uspesno obrisan!" });
            }
            else
                return Task.FromResult(new Poruka { Tekst = "Korisnik nije pronadjen!" });
        }
        public override Task<Poruka> IzmeniKorisnika(Korisnik korisnik, ServerCallContext context)
        {
            var listaKorisnika = Korisnici.Instanca().ListaKorisnika;

            Korisnik? k = listaKorisnika.FirstOrDefault(k => k.Id == korisnik.Id);

            if (k == null)
                return Task.FromResult(new Poruka 
                                        { Tekst = "Korisnik koga zelite da izmenite ne postoji!" });

            int index = listaKorisnika.FindIndex(kor => kor.Id == k.Id);

            listaKorisnika[index] = korisnik;

            return Task.FromResult(new Poruka { Tekst = $"Korisnik sa Id-jem {k.Id} " +
                                                          "je uspesno izmenjen" });
        }
        public override async Task PreuzmiKorisnike(OpsegId opseg, IServerStreamWriter<Korisnik> responseStream, ServerCallContext context)
        {
            var listaKorisnika = Korisnici.Instanca().ListaKorisnika
                                          .Where(k => k.Id >= opseg.IdOd && k.Id <= opseg.IdDo);

            foreach (var korisnik in listaKorisnika)
            {
                await responseStream.WriteAsync(korisnik);
            }
        }
        public override async Task ObrisiKorisnike(IAsyncStreamReader<Id> requestStream, IServerStreamWriter<Poruka> responseStream, ServerCallContext context)
        {
            var listaKorisnika = Korisnici.Instanca().ListaKorisnika;

            await foreach(var id in requestStream.ReadAllAsync())
            {
                Korisnik? korisnik = listaKorisnika.FirstOrDefault(k => k.Id == id.Id_);

                if (korisnik == null) 
                    await responseStream.WriteAsync(new Poruka 
                                { Tekst = $"Korisnik sa Id-jem {id.Id_} ne postoji!" });
                else
                {
                    listaKorisnika.Remove(korisnik);
                    await responseStream.WriteAsync(new Poruka 
                            { Tekst = $"Korisnik sa Id-jem {id.Id_} je obrisan!" });
                }
            }
        }
    }
}
