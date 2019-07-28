package maraphon.obstacles;

import maraphon.competitors.Team;
import maraphon.competitors.Competitor;

public class Course {
    private Obstacle[] obstacles;

    public Course(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    public void doIt(Team team) {
        System.out.println("3, 2, 1, Старт!");
        for (Competitor c: team.getTeammates()) {
            for (Obstacle o: obstacles) {
                if(c.isDistance()) {
                    o.doIt(c);
                }
                else {
                    System.out.println("Участник выбыл!");
                    break;
                }
            }
            System.out.println();
        }
    }
}
