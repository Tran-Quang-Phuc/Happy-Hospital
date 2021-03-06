package algorithm;

import classes.Position;

import java.util.ArrayList;
import java.util.Collections;

public class Astar {
    public double width;
    public double height;
    public Spot start;
    public Spot end;
    public ArrayList<Spot> ableSpot;
    public ArrayList<ArrayList<Spot>> grid;
    public ArrayList<Spot> path = new ArrayList<>();

    public Astar(
            double width,
            double height,
            Position startPos,
            Position endPos,
            ArrayList<Position> ablePos
    ) {
        this.width = width;
        this.height = height;
        this.start = new Spot(startPos.x, startPos.y);
        this.end = new Spot(endPos.x, endPos.y);

        this.grid = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            this.grid.set(i, new ArrayList<>());
            for (int j = 0; j < height; j++) {
                this.grid.get(i).set(j, new Spot(i, j));
            }
        }

        this.ableSpot = new ArrayList<>();
        for (int i = 0; i < ablePos.size(); i++) {
            this.ableSpot.add(this.grid.get(ablePos[i].x)[ablePos[i].y]);
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.grid.get(i).get(j).addNeighbors(this.ableSpot);
            }
        }
    }

    private double heuristic(Spot spot1, Spot spot2) {
        return Math.abs(spot1.i - spot2.i) + Math.abs(spot1.j - spot2.j);
    }

    private double heuristic2(Spot spot1, Spot spot2) {
        return Math.sqrt((spot1.i - spot2.i) * (spot1.i - spot2.i) + (spot1.j - spot2.j) * (spot1.j - spot2.j));
    }

    private boolean isInclude(Spot spot, ArrayList<Spot> spots) {
        for (int i = 0; i < spots.size(); i++) {
            if (spot.i == spots.get(i).i && spot.j == spots.get(i).j) return true;
        }
        return false;
    }

    public ArrayList<Position> cal() {
        ArrayList<Spot> openSet = new ArrayList<>();
        ArrayList<Spot> closeSet = new ArrayList<>();
        openSet.add(this.grid.get(this.start.i).get(this.start.j));
        int winner = 0;
        while (openSet.size() > 0) {
            for (int i = 0; i < openSet.size(); i++) {
                if (openSet.get(i).f < openSet.get(winner).f) {
                    winner = i;
                }
            }

            Spot current = openSet.get(winner);

            if (openSet.get(winner).equal(this.end)) {
                Spot cur = this.grid.get(this.end.i).get(this.end.j);
                this.path.add(cur);
                while (cur.previous != null) {
                    this.path.add(cur.previous);
                    cur = cur.previous;
                }
                Collections.reverse(this.path);
                ArrayList<Position> result = new ArrayList<>();
                for (int k = 0; k < this.path.size(); k++) {
                    result.add(new Position(this.path.get(k).i, this.path.get(k).j));
                }
                return result;
            }

            openSet.remove(winner);
            closeSet.add(current);

            final ArrayList<Spot> neighbors = current.neighbors;

            for (int i = 0; i < neighbors.size(); i++) {
                final Spot neighbor = neighbors.get(i);
                if (!this.isInclude(neighbor, closeSet)) {
                    double tempG = current.g + 1;
                    if (this.isInclude(neighbor, openSet)) {
                        if (tempG < neighbor.g) {
                            neighbor.g = tempG;
                        }
                    } else {
                        neighbor.g = tempG;
                        openSet.add((neighbor));
                    }

                    neighbor.h = this.heuristic2(neighbor, this.end);
                    neighbor.f = neighbor.h + neighbor.g;
                    neighbor.previous = current;
                } else {
                    double tempG = current.g + 1;
                    if (tempG < neighbor.g) {
                        openSet.add(neighbor);
                        final int index = closeSet.indexOf(neighbor);
                        if (index > -1) {
                            closeSet.remove(index);
                        }
                    }
                }
            }
        }
        System.out.println("Path not found");
        return null;
    }
}
class Spot {
    public int i;
    public int j;
    public double f;
    public double g;
    public double h;
    public ArrayList<Spot> neighbors = new ArrayList<>();
    public Spot previous;

    public Spot(int i, int j) {
        this.i = i;
        this.j = j;
        this.f = 0;
        this.g = 0;
        this.h = 0;
    }

    public void addNeighbors(ArrayList<Spot> ableSpot) {
        for (int k = 0; k < ableSpot.size(); k++) {
            if (this.i + 1 == ableSpot.get(k).i && this.j == ableSpot.get(k).j) {
                this.neighbors.add(ableSpot.get(k));
            } else if (this.i == ableSpot.get(k).i && this.j + 1 == ableSpot.get(k).j) {
                this.neighbors.add(ableSpot.get(k));
            } else if (this.i - 1 == ableSpot.get(k).i && this.j == ableSpot.get(k).j) {
                this.neighbors.add(ableSpot.get(k));
            } else if (this.i == ableSpot.get(k).i && this.j - 1 == ableSpot.get(k).j) {
                this.neighbors.add(ableSpot.get(k));
            }
        }
    }

    public boolean equal(Spot spot) {
        return this.i == spot.i && this.j == spot.j;
    }
}