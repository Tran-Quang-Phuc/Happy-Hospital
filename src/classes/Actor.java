package classes;

import classes.statistic.Constant;
import javafx.scene.Scene;
import java.util.HashSet;
import java.util.Set;
import classes.Text;

public class Actor {
    public static int  _id = 0; //Đếm số agv, số thứ tự agv cũng là ID của nó
    private final int agvID;
    private double expectedTime;
    public Set<Actor> collidedActors = new HashSet<>();

    public Actor(
        Scene scene,
        int x,
        int y,
        String texture,
        int frame
    ) {


        if(texture == "agv") {
            Actor._id++;
            this.agvID = Actor._id;
        } else {
            this.agvID = -1;//Ám chỉ đây là agent
        }
        this.collidedActors = new HashSet<>();
    }

    public int getAgvID() {
        return this.agvID;
    }

    public double getExpectedTimme() {
        return this.expectedTime;
    }

    public void estimateArrivalTime(int startX, int startY, int endX, int endY) {
        this.expectedTime = Math.floor(Math.sqrt((endX - startX)*(endX - startX) + (endY - startY)*(endY - startY))*0.085);
    }

    public void writeDeadline(Text table) {
        if(this.agvID != -1) {
            String enter = "";
            if(table.getText().length() > 0) enter = "\n";
            table.setText("DES_" + this.agvID + ": " +
                    Constant.secondsToHMS(this.expectedTime) + " ± " + Constant.DURATION() + enter + table.getText());

        }
    }

    public void eraseDeadline(Text table) {
        if(this.agvID != -1) {
            String enter = "";
            if(table.getText().length() > 0) enter = "\n";
            String erasedStr = "DES_" + this.agvID + ": " +
                    Constant.secondsToHMS(this.expectedTime) + " ± " + Constant.DURATION() + enter + table.getText();
            String tmp = table.getText();
            tmp.replace(erasedStr, "");
            table.setText(tmp);
        }
    }
}
