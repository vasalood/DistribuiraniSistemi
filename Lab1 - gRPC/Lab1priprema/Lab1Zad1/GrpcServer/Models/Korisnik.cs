namespace GrpcServer.Models
{
    public record Korisnik
    {
        public int Id { get; set; }
        public string? Ime { get; set; }
        public string? Prezime { get; set; }
        public string? Adresa { get; set; }
        public List<string>? BrojeviTelefona { get; set; }
    }
}
