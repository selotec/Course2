package maraphon.obstacles;

import maraphon.competitors.Competitor;

public class Water extends Obstacle {
    int distance;

    public Water(int distance) {
        this.distance = distance;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.swim(distance);
    }
}
