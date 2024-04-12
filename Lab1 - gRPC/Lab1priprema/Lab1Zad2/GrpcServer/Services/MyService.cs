using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using System.Diagnostics.Metrics;

namespace GrpcServer.Services
{
    public class MyService : GrpcServer.MyService.MyServiceBase
    {
        private static int acc = 1;
        private readonly ILogger<MyService> _logger;
        public MyService(ILogger<MyService> logger)
        {
            _logger = logger;
        }

        public override Task<Empty> OneData(IntValue value, ServerCallContext context)
        {
            var numbers = Enumerable.Range(1, value.Value);

            acc += numbers.Sum()/(value.Value);

            return Task.FromResult(new Empty());
        }

        public override async Task ManyData(IAsyncStreamReader<IntValue> requestStream,
                                            IServerStreamWriter<IntValue> responseStream,
                                            ServerCallContext context)
        {
            int counter = 0;

            await foreach (var value in requestStream.ReadAllAsync())
            {
                counter++;

                if (counter % 3 == 0)
                {
                    await responseStream.WriteAsync(new IntValue { Value = value.Value * acc });
                }
                else
                {
                    await responseStream.WriteAsync(new IntValue { Value = value.Value - acc });
                }
            }
        }
    }
}
