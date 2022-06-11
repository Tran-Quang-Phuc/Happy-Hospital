package classes.statesOfAutoAgv;

import javafx.scene.Scene;
import classes.AutoAgv;
import classes.statesOfAutoAgv.RunningState;
public class IdleState extends HybridState {
    private double _start;
    private  boolean _calculated;
    public IdleState(double _start) {
        super();
        this._start = _start;
        this._calculated = false;
    }
    @Override
    public void move(AutoAgv agv) {
        if(performance.now() - this._start < Constant.DURATION*1000) {
            if(!this._calculated) {
                this._calculated = true;
                double finish = this._start/1000;
                Scene mainScene =  (MainScene)agv.scene;
                double expectedTime = agv.getExpectedTime();
                if(finish >= expectedTime - Constant.DURATION && finish <= expectedTime + Constant.DURATION){
                    return;
                } else {
                    double diff = Math.max(expectedTime - Constant.DURATION - finish, finish - expectedTime - Constant.DURATION);
                    double lateness = Constant.getLateness(diff);
                    mainScene.harmfullness = mainScene.harmfullness + lateness;
                }
            }
            return;
        } else {
            // agv.firstText?.destroy();
            Scene mainScene =  (MainScene)agv.scene;
            if(mainScene != null)
                agv.eraseDeadline(mainScene.timeTable);
            agv.hybridState = new RunningState(true);
            // console.log((agv.hybridState as RunningState)._isLastMoving);
            agv.changeTarget();
        }
    }
}
