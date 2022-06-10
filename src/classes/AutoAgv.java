package classes;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AutoAgv extends Actor{
    public  Graph graph;
    public Node2D[] path
    public Node2D curNode;
    public Node2D endNode;
    public int cur;
    public double waitT;
    public int sobuocdichuyen;
    public double thoigiandichuyen;
    public HybridState  hybridState;
    public int endX;
    public int endY;
    public Text firstText;
    public Scene scene;

    public int startX;
    public int startY;
    public AutoAgv(
            Scene scene,
            int x,
            int y,
            int endX,
            int endY,
            Graph graph
    ) {
        super(scene, x * 32, y * 32, "agv");
        this.startX = x * 32;
        this.startY = y * 32;
        this.endX = endX * 32;
        this.endY = endY * 32;

        this.graph = graph;
        this.getBody().setSize(32, 32);
        this.setOrigin(0, 0);
        this.cur = 0;
        this.waitT = 0;
        this.curNode = this.graph.nodes[x][y];
        this.curNode.setState(StateOfNode2D.BUSY);
        this.endNode = this.graph.nodes[endX][endY];
        this.firstText = new Text(endX * 32, endY * 32, "DES");
        firstText.setFont(new Font(30));
        firstText.setFill(Color.BLACK);
        this.path = this.calPathAStar(this.curNode, this.endNode);
        this.sobuocdichuyen = 0;
        this.thoigiandichuyen = performance.now();
        this.estimateArrivalTime(x * 32, y * 32, endX * 32, endY * 32);
        this.hybridState = new RunningState();
    }
    protected void preUpdate( double time, double delta) {
        //this.move();
        // console.log(this.x, this.y);
        this.hybridState.move(this);
    }
    public Node2D[] calPathAStar(Node2D start, Node2D end) {
        return this.graph.calPathAStar(start, end);
    }
    public void changeTarget() {
        Scene mainScene=this.scene;
       int [] agvsToGate1= mainScene.mapOfExits.get(
                "Gate1")
        int [] agvsToGate2= mainScene.mapOfExits.get(
                "Gate2")
        String choosenGate = agvsToGate1[2] < agvsToGate2[2] ? "Gate1" : "Gate2";
        int [] newArray = mainScene.mapOfExits.get(choosenGate);
        newArray[2]++;
        mainScene.mapOfExits.set(choosenGate, newArray);

        this.startX = this.endX;
        this.startY = this.endY;

        int xEnd= newArray[0];
        int yEnd= newArray[1];

        this.endX = xEnd * 32;
        this.endY = yEnd * 32;

        int finalAGVs = (mainScene.mapOfExits.get(choosenGate))[2];

        this.endNode = this.graph.nodes[xEnd][yEnd];
        this.firstText = new Text(
                xEnd * 32,
                yEnd * 32,
                "DES_" + finalAGVs,
        );
        firstText.setFont(new Font(30));
        firstText.setFill(Color.BLACK);
        this.path = this.calPathAStar(this.curNode, this.endNode);
        this.cur = 0;
        this.sobuocdichuyen = 0;
        this.thoigiandichuyen = performance.now();
        this.estimateArrivalTime(
                32 * this.startX,
                32 * this.startY,
                this.endX * 32,
                this.endY * 32
        );
    }
}
