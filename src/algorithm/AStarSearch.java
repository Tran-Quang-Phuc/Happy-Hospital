package algorithm;

import classes.Position;

import java.sql.Array;
import java.util.ArrayList;

public class AStarSearch {
    public double width;
    public double height;
    public Spot start;
    public Spot end;
    public ArrayList<Spot> ableSpot = new ArrayList<>();
    public ArrayList<ArrayList<Spot>> grid;
    public ArrayList<Spot> path = new ArrayList<>();

    public AStarSearch(
            double width,
            double height,
            Position startPos,
            Position endPos,
            Position[] ablePos
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
    }
}

class Spot {
    public double i;
    public double j;
    public double f;
    public double g;
    public double h;
    public ArrayList<Spot> neighbors = new ArrayList<>();
    public Spot previous;

    public Spot(double i, double j) {
        this.i = i;
        this.j = j;
        this.f = 0;
        this.g = 0;
        this.h = 0;
    }

    public void addNeighbors(Spot[] ableSpot) {
        for (int k = 0; k < ableSpot.length; k++) {
            if (this.i + 1 == ableSpot[k].i && this.j == ableSpot[k].j) {
                this.neighbors.add(ableSpot[k]);
            } else if (this.i == ableSpot[k].i && this.j + 1 == ableSpot[k].j) {
                this.neighbors.add(ableSpot[k]);
            } else if (this.i - 1 == ableSpot[k].i && this.j == ableSpot[k].j) {
                this.neighbors.add(ableSpot[k]);
            } else if (this.i == ableSpot[k].i && this.j - 1 == ableSpot[k].j) {
                this.neighbors.add(ableSpot[k]);
            }
        }
    }

    public boolean equal(Spot spot) {
        if (this.i == spot.i && this.j == spot.j) return true;
        return false;
    }
}