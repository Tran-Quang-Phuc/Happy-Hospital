package algorithm.ProbTS;

import static algorithm.ProbTS.Constant._rng01;

public class PoissonDistribution implements Distribution {
    private double _min;
    private double _max;
    private double _mean;
    private double _variance;
    private DistributionType _type;
    private double _L;

    public PoissonDistribution(double lambda) {
        this._min = 0;
        this._max = Double.POSITIVE_INFINITY;
        this._mean = lambda;
        this._variance = lambda;
        this._type = DistributionType.Discrete;
        // Knuth's algorithm
        this._L = Math.exp(-lambda);
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
        // double k = 0;
        double p = 1;
        while (true) {
            p = p * _rng01();
            if(p <= this._L) {
                break;
            }
            // k++;
        }
        return p;
    }
}
