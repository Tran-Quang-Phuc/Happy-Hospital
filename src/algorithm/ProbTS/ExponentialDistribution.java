package algorithm.ProbTS;

import static algorithm.ProbTS.Constant._rng01;
// import static algorithm.ProbTS.Constant._rng11;


public class ExponentialDistribution implements Distribution {
    private double _min;
    private double _max;
    private double _mean;
    private double _variance;
    private DistributionType _type;

    public ExponentialDistribution(double lambda) {
        this._min = 0;
        this._max = Double.POSITIVE_INFINITY;
        this._mean = 1 / lambda;
        this._variance = Math.pow(lambda, -2);
        this._type = DistributionType.Continuous;
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
        return -1 * Math.log(_rng01()) * this._mean;
    }
}
