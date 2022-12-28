package tracker.app;

public enum Course {
    Java(600),
    DSA(400),
    Databases(480),
    Spring(550);

    private final int pointsRequired;

    Course(int pointsRequired) {
        this.pointsRequired = pointsRequired;
    }

    public int getPointsRequired() {
        return pointsRequired;
    }
}
