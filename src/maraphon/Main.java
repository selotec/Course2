package maraphon;

import maraphon.competitors.*;
import maraphon.obstacles.*;

public class Main {
    public static void main(String[] args) {
        Course bladeRun = new Course(new Obstacle[] {new Cross(400), new Wall(2), new Water(5)});
        Team threeInBoatAndDog = new Team(
                "3+1",
                new Competitor[] {
                        new Human("Иван"),
                        new Human("Петр"),
                        new Human("Федор"),
                        new Dog("Шарик") } );

        threeInBoatAndDog.teamInfo();
        bladeRun.doIt(threeInBoatAndDog);
        threeInBoatAndDog.showResults();
    }
}
