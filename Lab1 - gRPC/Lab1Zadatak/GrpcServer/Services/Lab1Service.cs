using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using GrpcServer.Protos;

namespace GrpcServer.Services
{
    public class Lab1Service : Lab1.Lab1Base
    {
        private readonly ILogger<Lab1Service> _logger;
        public Lab1Service(ILogger<Lab1Service> logger)
        {
            _logger = logger;
        }

        public override async Task<Empty> FirstProcedure(IAsyncStreamReader<IntData> requestStream, ServerCallContext context)
        {
            int sum = 0;
            int count = 0;

            await foreach (var data in requestStream.ReadAllAsync())
            {
                sum += data.Data;
                count++;

                //_logger.LogInformation($"Suma je { sum } a broj podataka je { count } acc je { Accumulator.Instance().Acc }");
            }

            if (count > 0)
            {
                Accumulator.Instance().Acc = sum / count;
                //_logger.LogInformation($"acc je {Accumulator.Instance().Acc}");
            }

            return new Empty();
        }

        public override async Task SecondProcedure(IAsyncStreamReader<IntData> requestStream,
                                                    IServerStreamWriter<IntData> responseStream,
                                                    ServerCallContext context)
        {
            int counter = 0;

            await foreach (var data in requestStream.ReadAllAsync())
            {
                counter++;

                if (counter % 2 == 0)
                {
                    await responseStream.WriteAsync(new IntData { Data = data.Data + 
                                                                        Accumulator.Instance().Acc });
                }
                else
                {
                    await responseStream.WriteAsync(new IntData { Data = data.Data * 
                                                                        Accumulator.Instance().Acc });
                }
            }
        }
    }
}