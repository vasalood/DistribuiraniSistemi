using Grpc.Core;
using Grpc.Net.Client;
using GrpcClient;

using var channel = GrpcChannel.ForAddress("https://localhost:7250");

var client = new ServiceKorisnik.ServiceKorisnikClient(channel);

string unos;
do
{
    Console.WriteLine("\nDodaj korisnika - a1");
    Console.WriteLine("Preuzmi korisnika - g1");
    Console.WriteLine("Obrisi korisnika - d1");
    Console.WriteLine("Izmeni korisnika - p1");
    Console.WriteLine("Preuzmi vise korisnika - gm");
    Console.WriteLine("Obrisi vise korisnika - dm");
    Console.WriteLine("Prekini izvrsenje programa - x");
    unos = Console.ReadLine();
    switch (unos)
    {
        case "a1":
            await DodajKorisnika();
            break;
        case "g1":
            await PreuzmiKorisnika();
            break;
        case "d1":
            await ObrisiKorisnika();
            break;
        case "p1":
            await IzmeniKorisnika();
            break;
        case "gm":
            await PreuzmiKorisnike();
            break;
        case "dm":
            await ObrisiKorisnike();
            break;
        case "x": break;
        default: break;
    }
} while (unos != "x");

async Task DodajKorisnika()
{
    try
    {
        int id = Int32.Parse(Console.ReadLine());
        string ime = Console.ReadLine();
        string prezime = Console.ReadLine();
        string adresa = Console.ReadLine();

        var korisnik = new Korisnik
        {
            Id = id,
            Ime = ime,
            Prezime = prezime,
            BrTelefona = { "0631133007", "027332657" }
        };

        if(!string.IsNullOrEmpty(adresa)) korisnik.Adresa = adresa;

        var response = await client.DodajKorisnikaAsync(korisnik);

        Console.WriteLine(response.Tekst);
    }
    catch(Exception ex)
    {
        Console.WriteLine(ex.Message);
    }
}

async Task PreuzmiKorisnika()
{
    try
    {
        Console.WriteLine("Unesite Id korisnika!");
        int id = Int32.Parse(Console.ReadLine());
        var response = await client.PreuzmiKorisnikaAsync(new Id
        {
            Id_ = id
        });

        if(response.Id != 0)
        {
            Console.WriteLine(response.Ime);
            Console.WriteLine(response.Prezime);
            if (response.Adresa != "") Console.WriteLine(response.Adresa);
            foreach (var fon in response.BrTelefona)
                Console.Write(fon + " ");
            Console.WriteLine();
        }
        else
        {
            Console.WriteLine("Korisnik ne postoji");
        }
    }
    catch(Exception ex)
    {
        Console.WriteLine(ex.Message);
    }
}

async Task ObrisiKorisnika()
{
    try
    {
        Console.WriteLine("Unesite Id korisnika!");
        int id = Int32.Parse(Console.ReadLine());

        var response = await client.ObrisiKorisnikaAsync(new Id { Id_ = id });

        Console.WriteLine(response.Tekst);
    }
    catch (Exception ex)
    {
        Console.WriteLine(ex.Message);
    }
}

async Task IzmeniKorisnika()
{
    try
    {
        var response = await client.IzmeniKorisnikaAsync(new Korisnik
                                    {
                                        Id = 1,
                                        Ime = "Mila",
                                        Prezime = "Petrovic",
                                        Adresa = "Trebinjska 23",
                                        BrTelefona = { "0610246321" }
                                    });

        Console.WriteLine(response.Tekst);
    }
    catch (Exception ex)
    {
        Console.WriteLine(ex.Message);
    }
}

async Task PreuzmiKorisnike()
{
    try
    {
        Console.WriteLine("Unesite donju granicu opsega");
        int idOd = Int32.Parse(Console.ReadLine());
        Console.WriteLine("Unesite gornju granicu opsega");
        int idDo = Int32.Parse(Console.ReadLine());

        var call = client.PreuzmiKorisnike(new OpsegId
                                            {
                                                IdOd = idOd,
                                                IdDo = idDo
                                            });

        await foreach(var response in call.ResponseStream.ReadAllAsync())
        {
            Console.WriteLine(response.Ime);
            Console.WriteLine(response.Prezime);
            if (response.Adresa != "") Console.WriteLine(response.Adresa);
            foreach (var fon in response.BrTelefona)
                Console.Write(fon + " ");
            Console.WriteLine();
        }

    }
    catch (Exception ex)
    {
        Console.WriteLine(ex.Message);
    }
}

async Task ObrisiKorisnike()
{
    var call = client.ObrisiKorisnike();

    try
    {
        var bgTask = Task.Run(async () =>
        {
            await foreach (var resp in call.ResponseStream.ReadAllAsync())
            {
                Console.WriteLine(resp.Tekst);
            }
        });

        while (true)
        {
            int id = Int32.Parse(Console.ReadLine());
            if (id == -1) break;

            await call.RequestStream.WriteAsync(new Id
            {
                Id_ = id
            });
        }

        await call.RequestStream.CompleteAsync();
        await bgTask;
    }
    catch (Exception ex)
    {
        Console.WriteLine(ex.Message);
    }
}