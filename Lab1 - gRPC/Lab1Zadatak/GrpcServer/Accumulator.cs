namespace GrpcServer
{
    public class Accumulator
    {
        public int Acc;
        private static Accumulator? instance;
        private static readonly object lockObj = new();

        private Accumulator(int defaultAcc) 
        {
            Acc = defaultAcc;
        }
        public static Accumulator Instance()
        {
            if(instance == null) 
            {
                lock(lockObj)
                {
                    instance ??= new Accumulator(12);
                }
            }
            return instance;
        }
    }
}
