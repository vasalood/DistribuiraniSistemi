// See https://aka.ms/new-console-template for more information

using Grpc.Core;
using Grpc.Net.Client;
using GrpcServer.Protos;

using var channel = GrpcChannel.ForAddress("https://localhost:7007");

var client = new Lab1.Lab1Client(channel);

string unos;
do
{
    Console.WriteLine("\nFirst procedure - fp");
    Console.WriteLine("Second procedure - sp");
    Console.WriteLine("Exit - x");
    unos = Console.ReadLine();
    switch (unos)
    {
        case "fp":
            await FirstProc();
            break;
        case "sp":
            await SecondProc();
            break;
        case "x": break;
        default: break;
    }
} while (unos != "x");

async Task FirstProc()
{
    try
    {
        Console.WriteLine("Input int values:");
        Console.WriteLine("-1 for end");

        var call2 = client.FirstProcedure();
        while (true)
        {
            int data = Int32.Parse(Console.ReadLine());
            if (data == -1) break;

            await call2.RequestStream.WriteAsync(new IntData
            {
                Data = data
            });
        }

        await call2.RequestStream.CompleteAsync();

        var resp = await call2;
    }
    catch (Exception e)
    {
        Console.WriteLine(e);
    }
}

async Task SecondProc()
{
    Console.WriteLine("Input int values: ");
    Console.WriteLine("anything but int - exit");
    var call = client.SecondProcedure();

    try
    {
        var bgTask = Task.Run(async () =>
        {
            await foreach (var data in call.ResponseStream.ReadAllAsync())
            {
                Console.WriteLine("Value: " + data.Data);
            }
        });

        while (true)
        {
            int data;
            if (!Int32.TryParse(Console.ReadLine(), out data)) break;
            await call.RequestStream.WriteAsync(new IntData { Data = data });
        }

        await call.RequestStream.CompleteAsync();
        await bgTask;



    }
    catch (Exception ex)
    {
        Console.WriteLine(ex.Message);
    }
}