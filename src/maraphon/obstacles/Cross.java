package maraphon.obstacles;

import maraphon.competitors.Competitor;

public class Cross extends Obstacle {
    int distance;

    public Cross(int distance) {
        this.distance = distance;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.run(distance);
    }
}
