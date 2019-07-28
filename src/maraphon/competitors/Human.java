package maraphon.competitors;

public class Human implements Competitor {
    String name;

    int maxRunDistance = 3000;
    int maxJumpHeight = 2;
    int maxSwimDistance = 100;

    boolean active;

    public Human(String name) {
        this.name = name;
        active = true;
    }


    @Override
    public void run(int dist) {
        if(dist <= maxRunDistance) {
            System.out.println(name + " пробежал кросс");
        } else {
            System.out.println(name + " провалил кросс");
            active = false;
        }
    }

    @Override
    public void jump(int height) {
        if(height <= maxJumpHeight) {
            System.out.println(name + " справился с прыжком");
        } else {
            System.out.println(name + " провалил прыжок");
            active = false;
        }
    }

    @Override
    public void swim(int dist) {
        if(maxSwimDistance == 0) {
            System.out.println(name + " не умеет плавать");
            active = false;
            return;
        }

        if(dist <= maxSwimDistance) {
            System.out.println(name + " удачно проплыл");
        } else {
            System.out.println(name + " не смог проплыть");
            active = false;
        }
    }

    @Override
    public boolean isDistance() {
        return active;
    }

    @Override
    public void info() {
        System.out.println(name + " на дистанции: " + active);
    }
}
