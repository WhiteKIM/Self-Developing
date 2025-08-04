package whitekim.self_developing.model.type;

public enum Difficulty {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

    private int level;

    Difficulty(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
