package classes;

import javafx.scene.Scene;

import javax.swing.text.html.parser.Entity;
import java.util.HashSet;
import java.util.Set;

public class Actor {
    private static int  _id = 0; //Đếm số agv, số thứ tự agv cũng là ID của nó
    private int agvID;
    private double expectedTime;
    public Set<Actor> collidedActors = new HashSet<>();

    public Actor(
        Scene scene,
        int x,
        int y,
        String texture,
        int frame
    ) {
        
    }
}
