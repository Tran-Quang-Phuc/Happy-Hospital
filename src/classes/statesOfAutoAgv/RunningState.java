package classes.statesOfAutoAgv;

import javafx.scene.Scene;
import classes.AutoAgv;

public class RunningState extends HybridState {
    public boolean _isLastMoving;
    private boolean _agvIsDestroyed;

    public RunningState() {
        super();
        this._isLastMoving = false;
        this._agvIsDestroyed = false;
    }
    @Override
    public void move(AutoAgv agv) {
        if(this._agvIsDestroyed)
            return;
        if(!agv.path)
            return;
        if(agv.cur == agv.path.length - 1) {
            // agv.setVelocity(0, 0);
            if(this._isLastMoving) {
                Scene mainScene = (MainScene)agv.scene;

            }
        }
    }
}
