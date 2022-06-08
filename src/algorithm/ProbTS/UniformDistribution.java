package algorithm.ProbTS;

import static algorithm.ProbTS.Constant._rng01;

public class UniformDistribution implements Distribution {
    private double _min;
    private double _max;
    private double _range;
    private double _mean;
    private double _variance;
    private DistributionType _type;
    public UniformDistribution(double min, double max) {
        this._min = min;
        this._max = max;
        this._range = (max - min);
        this._mean = min + this._range / 2;
        this._variance = ((max - min) * (max - min)) / 12;
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
        return this._min + _rng01() * this._range;
    }
}
