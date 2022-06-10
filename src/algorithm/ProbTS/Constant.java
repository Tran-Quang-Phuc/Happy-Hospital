package algorithm.ProbTS;

enum DistributionType {
    Unknown,
    Continuous,
    Discrete
}
interface Distribution {
    double min = 0;
    double max = 0;
    double mean = 0;
    double variance = 0;
    DistributionType tyoe = DistributionType.Unknown;

    double random();
}

public class Constant {
    public static double _rng01() {
        return Math.random();
    }
    public static double _rng11() {
        return ((double)(int)(_rng01() * 0x100000000L)) / 0x100000000L * 2;
    }
}
