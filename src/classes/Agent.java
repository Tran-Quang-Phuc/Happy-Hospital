package classes;

import algorithm.Astar;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.awt.*;
import java.util.ArrayList;

public class Agent extends Actor{
    private Position startPos;
    private Position endPos;
    private ArrayList<Position> groundPos;
    private ArrayList<Position> path;    //đường đi được tính bởi AStar
    private ArrayList<Position> vertexs; //đường đi thực tế của agent
    private Text endText;
    private Text agentText;
    private Astar astar;
    private int next = 1;
    private int id;
    public boolean isOverlap = false;
    public boolean isActive = true;
    public double speed = 38;

    public Agent(
            Scene scene,
            Position startPos,
            Position endPos,
            ArrayList<Position> groundPos,
            int id
    ) {
        super(scene, startPos.x * 32, startPos.y * 32, "tiles_spr", 17);
        this.startPos = startPos;
        this.endPos = endPos;
        this.groundPos = groundPos;
        this.path = new ArrayList<Position>();
        this.vertexs = new ArrayList<Position>();
        this.id = id;
        this.speed = Math.floor(Math.random() * (this.speed - 10)) + 10;

        this.endText = new Text();
        this.agentText = new Text();


        this.astar = new Astar(52, 28, startPos, endPos, groundPos);
        this.path = this.astar.cal();   //tính đường đi cho agent từ thuật toán tìm đường AStar, đường đi thực tế sẽ dựa trên đường đi tính được từ thuật toán
        
        this.initVertexs();
        
        this.setSize(31, 31);
        this.setOrigin(0, 0);
    }

    public void goToDestinationByVertexs() {
        if(this.next == this.vertexs.size()) {   //nếu vertex tiếp theo là vertex cuối - chạm đích - thì agent di chuyển đến và DONE
            this.agentText.setText("DONE");
            this.agentText.setFont(Font.font(12));
            this.agentText.setX(this.x - 1);
            this.x = this.vertexs.get(this.vertexs.size() - 1).x * 32;
            this.y = this.vertexs.get(this.vertexs.size() - 1).y * 32;
            this.setVelocity(0);
            this.eliminate();
            return;
        }
        if(
                Math.abs(this.vertexs.get(this.next).x * 32 - this.x) > 1 ||
                Math.abs(this.vertexs.get(this.next).y * 32 - this.y) > 1
        ) { //nếu vertex tiếp theo chưa phải vertex cuối thì di chuyển đến vertex tiếp theo
            this.moveTo(
                    this,
                    this.vertexs.get(this.next).x * 32,
                    this.vertexs.get(this.next).y * 32,
                    this.speed
            );
            this.agentText.setX(this.x);
            this.agentText.setY(this.y - 16);
        } else {
            this.next++;
        }
    }

    public void addRandomVertexs(Position start, Position end) {
        double dis = Math.sqrt((start.x - end.x)*(start.x - end.x) + (start.y - end.y)*(start.y - end.y));
        double num = Math.ceil((dis * 32) / 50);
        for(int i = 0; i < num; i++) {  //chia đoạn đường từ startPos tới endPos thành những đoạn nhỏ hơn, giữa 2 Pos này có nhiều random Vertexs
            while (true) {
                Position rV = new Position(
                        (((end.x - start.x) / num) * i + start.x + (Math.random() - 0.5)),
                        (((end.y - start.y) / num) * i + start.y + (Math.random() - 0.5))
                );
                Position _1, _2, _3, _4;
                boolean b_1, b_2, b_3, b_4;

                _1 = new Position(rV.x, rV.y);
                _2 = new Position(rV.x + 1, rV.y);
                _3 = new Position(rV.x + 1, rV.y + 1);
                _4 = new Position(rV.x, rV.y + 1);

                b_1 = b_2 = b_3 = b_4 = false;

                for(int j = 0; j < this.groundPos.size(); j++) {    //kiểm tra tính hợp lệ của Vertex tiếp theo
                    Position p = this.groundPos.get(j);
                    if (_1.x < p.x + 1 && _1.y < p.y + 1 && _1.x >= p.x && _1.y >= p.y) {
                        b_1 = true;
                    }
                    if (_2.x < p.x + 1 && _2.y < p.y + 1 && _2.x >= p.x && _2.y >= p.y) {
                        b_2 = true;
                    }
                    if (_3.x < p.x + 1 && _3.y < p.y + 1 && _3.x >= p.x && _3.y >= p.y) {
                        b_3 = true;
                    }
                    if (_4.x < p.x + 1 && _4.y < p.y + 1 && _4.x >= p.x && _4.y >= p.y) {
                        b_4 = true;
                    }
                }
                if (b_1 && b_2 && b_3 && b_4) {
                    this.vertexs.add(rV);
                    break;
                }
            }
        }
    }

    private void initVertexs() {
        if(!path.isEmpty()) {
            this.vertexs.add(this.path.get(0));
            int pathLength = this.path.size();
            for(int cur = 2; cur < pathLength; cur++) {
                if(
                        (this.path.get(cur).x == this.path.get(cur - 1).x &&
                         this.path.get(cur).x == this.path.get(cur - 2).x) ||
                        (this.path.get(cur).y == this.path.get(cur - 1).y &&
                         this.path.get(cur).y == this.path.get(cur - 2).y)
                ) { continue; } //nếu các Pos trên đoạn path này nằm trên đoạn thẳng nằm ngang hoặc dọc thì không cần tạo random Vertex

                Position curV = this.vertexs.get(this.vertexs.size() - 1);  //curV = vertex đang xét
                Position nextV = this.vertexs.get(cur - 1); //nextV = vertex tiếp theo
                this.addRandomVertexs(curV, nextV); //tạo đường đi từ Pos hiện đang xét của Path tới Pos tiếp theo bằng cách sinh ra các random Vertexs
                this.vertexs.add(nextV);
            }
            this.addRandomVertexs(this.vertexs.get(this.vertexs.size() - 1), this.path.get(pathLength - 1));
            this.vertexs.add(this.path.get(pathLength - 1));
        }
    }

    public void preUpdate() {
        this.goToDestinationByVertexs();
    }

    public Position getStartPos() {
        return this.startPos;
    }

    public Position getEndPos() {
        return this.endPos;
    }

    public int getId() {
        return this.id;
    }

    public void eliminate() {
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void pause() {
        this.setVelocity(0);
        this.setActive(false);
    }

    public void restart() {
        this.setActive(true);
    }

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    public void handleOverlap() {
        if(this.isOverlap) return;
        this.isOverlap = true;
        setTimeout(() -> {
                this.isOverlap = false;
        }, 4000);
        double r = Math.random();
        if(r < 0.5) {
            return;
        } else {
            this.setVelocity(0);
            this.setActive(false);
            setTimeout(() -> {
                    this.setActive(true);
            }, 2000);
        }
    }
}
