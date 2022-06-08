package algorithm;

import algorithm.ProbTS.*;

public class RandomDistribution {
    private String _name;

    public double getProbability() {
        Prob Prob = new Prob();
        double ran = Math.random();
        ran = Math.floor(ran * 4);
        switch(ran) {
            case 0d:
                PoissonDistribution poisson = Prob.poisson(); //Math.random();
                this._name = "Poisson";
                return poisson.random();
            case 1d:
                UniformDistribution uniform = Prob.uniform();
                this._name = "Uniform";
                return uniform.random();
            case 2d:
                this._name = "Bimodal";
                BimodalDistribution bimodal = Prob.bimodal();
                return bimodal.random();
            default: 
                break;    
        }
        this._name = "Normal";
        NormalDistribution normal = Prob.normal();
        return normal.random();  
    }

    public String get_name() {
        return this._name;
    }
    
}
