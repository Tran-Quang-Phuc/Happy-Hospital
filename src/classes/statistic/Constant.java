package classes.statistic;

import classes.Actor;
import classes.Node2D;

import java.util.Set;

enum ModeOfPathPlanning {
        FRANSEN,
        PROPOSE
}

public class Constant {
        public static final int DURATION = 4;    //thời gian AutoAgv đợi để nhận/dỡ hàng khi đến đích
        public static double getLateness(double x) { return 5*x; };
        public static final int SAFE_DISTANCE = 46;
        public static final int DELTA_T = 10;
        public static final ModeOfPathPlanning MODE = ModeOfPathPlanning.FRANSEN;

        public static String secondsToHMS(double seconds) {
                double h = Math.floor(seconds % (3600*24) / 3600);
                double m = Math.floor(seconds % 3600 / 60);
                double s = Math.floor(seconds % 60);

                String hDisplay = (h >= 10) ? String.valueOf((int)h) : ("0" + String.valueOf((int)h));
                String mDisplay = (m >= 10) ? String.valueOf((int)m) : ("0" + String.valueOf((int)m));
                String sDisplay = (s >= 10) ? String.valueOf((int)s) : ("0" + String.valueOf((int)s));
                return hDisplay + ":" + mDisplay + ":" + sDisplay;
        }

        public static double minDistance(Actor actor, Set<Actor> otherActor) {
                double dist = Double.POSITIVE_INFINITY;
                for(Actor element : otherActor) {
                        double smaller = Math.sqrt((element.x - actor.x)*(element.x - actor.x) + (element.y - actor.y)*(element.y - actor.y));
                        if(dist > smaller) dist = smaller;
                }
                return dist;
        }

        public static int numberOfEdges(double width, double height, Node2D[][]) {
                int count = 0;

        }
}
