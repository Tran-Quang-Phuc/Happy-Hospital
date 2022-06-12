package classes.statistic;

public class Constant {
        public static int DURATION() {return 4;}    //thời gian AutoAgv đợi để nhận/dỡ hàng khi đến đích


        public static String secondsToHMS(double seconds) {
                double h = Math.floor(seconds % (3600*24) / 3600);
                double m = Math.floor(seconds % 3600 / 60);
                double s = Math.floor(seconds % 60);

                String hDisplay = (h >= 10) ? String.valueOf((int)h) : ("0" + String.valueOf((int)h));
                String mDisplay = (m >= 10) ? String.valueOf((int)m) : ("0" + String.valueOf((int)m));
                String sDisplay = (s >= 10) ? String.valueOf((int)s) : ("0" + String.valueOf((int)s));
                return hDisplay + ":" + mDisplay + ":" + sDisplay;
        }
}
