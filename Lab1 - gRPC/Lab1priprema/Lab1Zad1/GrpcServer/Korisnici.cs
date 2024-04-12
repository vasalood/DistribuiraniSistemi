namespace GrpcServer
{
    public class Korisnici
    {
        public List<Korisnik> ListaKorisnika;
        private static Korisnici? instanca;
        private static readonly object lockObj = new();

        private Korisnici()
        {
            ListaKorisnika = new List<Korisnik>();
        }

        public static Korisnici Instanca()
        {
            if (instanca == null)
            {
                lock(lockObj)
                {
                    instanca ??= new Korisnici();
                }
            }

            return instanca;
        }
    }
}
