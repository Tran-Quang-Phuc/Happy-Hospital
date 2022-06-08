package algorithm.ProbTS;

public class Prob {
    public UniformDistribution uniform() {
        return new UniformDistribution(0, 1);
    }
    public NormalDistribution normal() {
        return new NormalDistribution(0, 1);
    }
    public ExponentialDistribution exponential() {
        return new ExponentialDistribution(1);
    }
    public LogNormalDistribution logNormal() {
        return new LogNormalDistribution(0, 1);
    }
    public PoissonDistribution poisson() {
        return new PoissonDistribution(1);
    }
    public BimodalDistribution bimodal() {
        return new BimodalDistribution(1);
    }

}
