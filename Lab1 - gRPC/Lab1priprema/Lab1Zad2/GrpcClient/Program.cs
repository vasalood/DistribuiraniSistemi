using Grpc.Core;
using Grpc.Net.Client;
using GrpcClient;

using var channel = GrpcChannel.ForAddress("https://localhost:7029");
var client = new MyService.MyServiceClient(channel);

string unos;

do
{
    Console.WriteLine("Za slanje jednog podatka unesite: od");
    Console.WriteLine("Za slanje vise podataka unesite: md");
    Console.WriteLine("Za prekid programa unesite: x");
    unos = Console.ReadLine();

    if(unos == "od")
    {
        await SendOneData();
    }
    else if(unos == "md")
    {
        await SendManyData();
    }
    else if(unos != "x")
    {
        Console.WriteLine("Dozvoljene komande su od, md, x");
    }
    
} while (unos != "x");

async Task SendOneData()
{
    int data;

    while (true)
    {
        Console.WriteLine("Unesite celobrojnu vrednost za slanje!");
        data = Int32.Parse(Console.ReadLine());

        if (data > 0) break;

        Console.WriteLine("Vrednost mora da bude pozitivna");
    }

    try
    {
        await client.OneDataAsync(new IntValue { Value = data });
        Console.WriteLine("Uspesno slanje jednog podatka!");
    }
    catch (Exception ex)
    {
        Console.WriteLine(ex.Message);
    }
}

async Task SendManyData()
{
    Console.WriteLine("Unosite celobrojne vrednosti! Za prekid streama bilo koja necelobrojna vrednost");
    var call = client.ManyData();

    try
    {
        var bgTask = Task.Run(async() =>
        {
            await foreach(var value in call.ResponseStream.ReadAllAsync())
            {
                Console.WriteLine("Vrednost je: " + value.Value);
            }
        });

        while(true)
        {
            int data;
            if (!Int32.TryParse(Console.ReadLine(), out data)) break;
            await call.RequestStream.WriteAsync(new IntValue { Value = data });
        }

        await call.RequestStream.CompleteAsync();
        await bgTask;
    }
    catch (Exception ex)
    {
        Console.WriteLine(ex.Message);        
    }
}