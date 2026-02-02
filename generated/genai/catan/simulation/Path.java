package catan.simulation;

public class Path {

    private String id;
    private Intersection endPointA;
    private Intersection endPointB;
    private Road occupant;

    public Path(String id, Intersection a, Intersection b) {
        this.id = id;
        this.endPointA = a;
        this.endPointB = b;
    }

    public Intersection[] getEndpoints() {
        return new Intersection[]{endPointA, endPointB};
    }

    public Road getOccupant() {
        return occupant;
    }

    public void setOccupant(Road road) {
        this.occupant = road;
    }

    public String getId() {
        return id;
    }
}