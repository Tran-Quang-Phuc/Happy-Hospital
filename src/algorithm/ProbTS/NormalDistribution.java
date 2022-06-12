package algorithm.ProbTS;

//import static algorithm.ProbTS.classes.statistic.Constant._rng01;
import static algorithm.ProbTS.Constant._rng11;


public class NormalDistribution implements Distribution {
    private double _min;
    private double _max;
    private double _mean;
    private double _sd;
    private double _variance;
    private DistributionType _type;
    // private double _y1;
    // private double _y2;

    public NormalDistribution (double mean, double sd) {
        this._min = Double.NEGATIVE_INFINITY;
        this._max = Double.POSITIVE_INFINITY;
        this._mean = mean;
        this._sd = sd;
        this._variance = sd * sd;
        this._type = DistributionType.Continuous;
        // this._y1 = null;
        // this._y2 = null;
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
        double M = 1/(this._sd * Math.sqrt(Math.PI * 2));
        double x = _rng11() - this._mean;
        double w = Math.exp(-x*x/(2 * this._variance));
        return M*w;
    }
}
