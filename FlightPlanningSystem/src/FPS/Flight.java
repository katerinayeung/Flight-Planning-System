package FPS;

public class Flight {
    public static void main(String[] args) {
        // airplane test
        Airplane testPlane = new Airplane("Boeing", "747", "Commercial", 100000, 600, 100);
        testPlane.calculateRange();
    }
}