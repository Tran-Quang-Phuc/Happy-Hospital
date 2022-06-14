package classes;

import classes.Position;
import classes.Graph;
import classes.statistic.Constant;
import java.util.Objects;

class EmergencyGraph extends Graph {
    public Node2D[][] virtualNodes;
    public EmergencyGraph(int width, int height, Position[][][] danhsachke, Position[] pathPos)
    {
        super(width, height, danhsachke, pathPos);
        this.virtualNodes = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.virtualNodes[i][j] = new Node2D(i, j, true); //new VirtualNode(i, j, true);
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < danhsachke[i][j].length; k++) {
                    Node2D nutke = danhsachke[i][j][k];
                    this.nodes[i][j].setNeighbor(this.virtualNodes[nutke.x][nutke.y]);
                    this.virtualNodes[i][j].setNeighbor(this.virtualNodes[nutke.x][nutke.y]);
                    this.virtualNodes[i][j].setNeighbor(this.nodes[nutke.x][nutke.y]);
                }
            }
        }
        for (Position p : pathPos) {
            this.virtualNodes[p.x][p.y].setState(StateOfNode2D.EMPTY);
        }
    }

    public void updateState() {
        super.updateState();
        for(int j = 0; j < this.width; j++) {
            for(int k = 0; k < this.height; k++) {
                int x = this.nodes[j][k].x;
                int y = this.nodes[j][k].y;
                this.nodes[j][k].weight = 0;
                this.virtualNodes[j][k].weight = 0;
                for (Agent agent : this.agents) {
                    float dist = Math.sqrt((x - agent.x) * * 2 + (y - agent.y) **2);
                    if (dist / agent.speed < Constant.DELTA_T) {
                        this.nodes[j][k].weight++;
                    }
                }
                if(this.getAutoAgvs() != null) {
                    this.getAutoAgvs().forEach((item) -> {
                    if (item.path) {
                        for (int i = 0; i < item.path.length; i++) {
                            if (item.path[i].isVirtualNode) {
                                if (item.path[i].x == this.virtualNodes[j][k].x && item.path[i].y == this.virtualNodes[j][k].y) {
                                    this.virtualNodes[j][k].weight++;
                                }
                            } else {
                                if (Objects.equals(item.path[i], this.nodes[j][k])) {
                                    this.nodes[j][k].weight++;
                                }
                            }
                        }
                    }
            }
          );
                }
            }
        }
    }

    public Node2D[] calPathAStar(Node2D start, Node2D end) {
        /*
         * Khoi tao cac bien trong A*
         */
        Node2D[] openSet = {start};
        Node2D[] closeSet = new Node2D[0];
        Node2D[] path = new Node2D[0];
        int[][] astar_f = new int[width][height];
        int[][] astar_g = new int[width][height];
        int[][] astar_h = new int[width][height];
        Node2D[][] previous = new Node2D[width][height];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                astar_f[i][j] = 0;
                astar_g[i][j] = 0;
                astar_h[i][j] = 0;
            }
        }
        int lengthOfPath = 1;
        /*
         * Thuat toan
         */
        push(openSet, this.nodes[start.x][start.y]);
        while (openSet.length > 0) {
            int winner = 0;
            for (int i = 0; i < openSet.length; i++) {
                if (astar_f[openSet[i].x][openSet[i].y] < astar_f[openSet[winner].x][openSet[winner].y]) {
                    winner = i;
                }
            }
            Node2D current = openSet[winner];
            if (Objects.equals(openSet[winner], end)) {
                Node2D cur = this.nodes[end.x][end.y];
                push(path, cur);
                while (previous[cur.x][cur.y] != null) {
                    push(path, previous[cur.x][cur.y]);
                    cur = previous[cur.x][cur.y];
                }
                reverse(path);
                return path;
            }
            remove_ele(openSet, winner);
            push(closeSet, current);
            Node2D[] neighbors = {current.nodeN, current.nodeE, current.nodeS, current.nodeW,
                    current.nodeVN, current.nodeVE, current.nodeVS, current.nodeVW};

            for (Node2D neighbor : neighbors) {
                if (neighbor != null) {
                    if (!this.isInclude(neighbor, closeSet)) {
                        int timexoay = 0;
                        if (previous[current.x][current.y] &&
                                neighbor.x != previous[current.x][current.y].x &&
                                neighbor.y != previous[current.x][current.y].y)
                        {
                            timexoay = 1;
                        }
                        int tempG = astar_g[current.x][current.y] + 1 + current.getW() + timexoay;
                        if (super.isInclude(neighbor, openSet)) {
                            if (tempG < astar_g[neighbor.x][neighbor.y]) {
                                astar_g[neighbor.x][neighbor.y] = tempG;
                            }
                        } else {
                            astar_g[neighbor.x][neighbor.y] = tempG;
                            push(openSet, neighbor);
                            lengthOfPath++;
                        }
                        astar_h[neighbor.x][neighbor.y] = this.heuristic(neighbor, end);
                        astar_f[neighbor.x][neighbor.y] = astar_h[neighbor.x][neighbor.y] + astar_g[neighbor.x][neighbor.y];
                        previous[neighbor.x][neighbor.y] = current;
                    }//end of if (!this.isInclude(neighbor, closeSet)) {
                }
            }
        }//end of while (openSet.length > 0)
        System.out.println("Path not found!");
        return null;
    }
}
