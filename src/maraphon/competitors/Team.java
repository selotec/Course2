package maraphon.competitors;

public class Team {
    private String name;
    private Competitor[] teammates;

    public Team(String name, Competitor[] teammates) {
        this.name = name;
        this.teammates = teammates;
    }

    public Competitor[] getTeammates() {
        return teammates;
    }

    public void teamInfo() {
        System.out.println("Команда \"" + name + "\":");
        for (Competitor c: teammates) {
            if(c.isDistance()) c.info();
        }
        System.out.println();
    }

    public void showResults() {
        System.out.println("Команда \"" + name + "\", прошли дистанцию:");
        for (Competitor c: teammates) {
            if(c.isDistance()) c.info();
        }
    }
}
