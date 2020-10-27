
public class ResponseItem {
    private int Type;
    private double[][] Points;
    private int[] Classes;
    private double X;
    private double Y;


    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public double[][] getPoints() {
        return Points;
    }

    public void setPoints(double[][] points) {
        this.Points = points;
    }

    public int[] getClasses() {
        return Classes;
    }

    public void setClasses(int[] classes) {
        this.Classes = classes;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        this.X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        this.Y = y;
    }
}
