package algorithm.ProbTS;

public class LogNormalDistribution implements Distribution{
    private double _min;
    private double _max;
    private double _mean;
    private double _variance;
    private DistributionType _type;
    private NormalDistribution _nf;

    public LogNormalDistribution(double mu, double sigma)
    {
        this._min = 0;
        this._max = Double.POSITIVE_INFINITY;
        this._mean = Math.exp(mu + ((sigma * sigma) / 2));
        this._variance = (Math.exp(sigma * sigma) - 1) * Math.exp(2 * mu + sigma * sigma);
        this._type = DistributionType.Continuous;
        this._nf = new NormalDistribution(mu, sigma);
    }

    public double get_min() {
        return _min;
    }

    public double get_max() {
        return _max;
    }

    public double get_mean() {
        return _mean;
    }

    public double get_variance() {
        return _variance;
    }

    public DistributionType get_type() {
        return _type;
    }

    @Override
    public double random() {
        return Math.exp(this._nf.random());
    }
}
