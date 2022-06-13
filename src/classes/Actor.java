package classes;

import classes.statistic.Constant;
import javafx.scene.Scene;
import java.util.HashSet;
import java.util.Set;
import classes.Text;

public class Actor {

    Scene scene;
    int x;
    int y;
    int width;
    int height;
    int velocity;
    String texture;
    int frame;
    boolean collideWorldBounds;
    public static int  _id = 0; //Đếm số agv, số thứ tự agv cũng là ID của nó
    private final int agvID;
    private double expectedTime;
    public HashSet<Actor> collidedActors = new HashSet<>();


    public Actor(
        Scene scene,
        int x,
        int y,
        String texture,
        int frame
    ) {

        this.scene = scene;
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.frame = frame;
        this.collideWorldBounds = true;

        if(texture == "agv") {
            Actor._id++;
            this.agvID = Actor._id;
        } else {
            this.agvID = -1;//Ám chỉ đây là agent
        }
        this.collidedActors = new HashSet<>();
    }

    public Actor(
            Scene scene,
            int x,
            int y,
            String texture
    ) {
        this(scene, x, y, texture, 0);
    }

    protected void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    protected void setOrigin(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void freeze(Actor actor) {
        if(this.collidedActors == null) {
            this.collidedActors = new HashSet<>();
        }
        if(!this.collidedActors.contains(actor)) {
            this.collidedActors.add(actor);
        }
    }
}
