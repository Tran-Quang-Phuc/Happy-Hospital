package classes;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;


public class Agv extends Actor {
    private Text text;
    Scene scene;
    private Tilemaps.TilemapLayer pathLayer;
    public boolean isImmortal = false; // biến cần cho xử lý overlap =))
    private boolean  isDisable= false; // biến cần cho xử lý overlap =))
    private int desX;
    private int desY;
    private Text desText;
    boolean up=false,down=false,right=false,left=false;
    public Agv(
            Scene scene,
            int x,
            int y,
            int desX,
            int desY,
            Tilemaps.TilemapLayer pathLayer
    ) {
        super(scene, x, y, "agv");
        this.desX = desX;
        this.desY = desY;
        this.pathLayer = pathLayer;
        desText= new Text(desX,desY,"DES");
        desText.setFill(Color.BLACK);
        desText.setFont(new Font(30));
        this.text=new Text(x,y-2,"AGV");
        text.setFill(Color.BLACK);
        text.setFont(new Font(30));
        // KEYS

        // PHYSICS
        this.getBody().setSize(32, 32);
        this.setOrigin(0, 0);

        this.estimateArrivalTime(x, y, desX, desY);
    }
    private Tilemaps.Tile[] getTilesWithin(): {
        return this.pathLayer.getTilesWithinWorldXY(this.x, this.y, 31, 31);
    }

    public void ToastInvalidMove(){
        Text text=new Text();
        text.setFont(new Font(30));
        text.setX(45); text.setY(150);
        text.setFill(Color.BLACK);
        text.setText("Di chuyển không hợp lệ !");

        //SHOW MESSAGE IS MISSING

    }
    public void ToastOverLay(){
        Text text=new Text();
        text.setFont(new Font(30));
        text.setX(45); text.setY(150);
        text.setFill(Color.BLACK);
        text.setText("AGV va chạm với Agent !");

        //SHOW MESSAGE IS MISSING

    }
    public void update(){
        this.getBody().setVelocity(0);

        //SHOW MESSAGE "AGV"

        if(this.isDisable) return;

        Tilemaps.Tile[] tiles =this.getTilesWithin();

        boolean t,l,b,r;
        t=true; l=true; r=true;b=true;

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case UP: up=true;break;
                    case DOWN: down=true;break;
                    case LEFT: left=true;break;
                    case RIGHT: right=true;break;
                }
            }
        });

        for(int i=0;i< tiles.length;i++){
            if (tiles[i].properties.direction == "top") {
                b = false;
                if (this.down==true) {
                    this.ToastInvalidMove();
                }
            }
            if (tiles[i].properties.direction == "left") {
                r = false;
                if (this.right==true) {
                    this.ToastInvalidMove();
                }
            }
            if (tiles[i].properties.direction == "bottom") {
                t = false;
                if (this.up==true) {
                    this.ToastInvalidMove();
                }
            }
            if (tiles[i].properties.direction == "right") {
                l = false;
                if (this.left==true) {
                    this.ToastInvalidMove();
                }
            }
        }
        if(this.up==true){
            if(t==true){
                //DI CHUYEN LEN TREN
            }
        }
        if(this.down==true){
            if(b==true){
                //DI CHUYEN XUONG DUOI
            }
        }
        if(this.left==true){
            if(l==true){
                //DI CHUYEN SANG TRAI
            }
        }
        if(this.right==true){
            if(r==true){
                //DI CHUYEN SANG PHAI
            }
        }
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case UP: up=false;break;
                    case DOWN: down=false;break;
                    case LEFT: left=false;break;
                    case RIGHT: right=false;break;
                }
            }
        });
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
    public void handleOverlap(){
        this.ToastOverLay();
        if(!this.isImmortal){
            this.isDisable=true;
            setTimeout(()->{
                this.isImmortal=true;
                this.isDisable=false;
                setTimeout(()->{this.isImmortal=false},2000);},1000);
            }
        }
    }

}
