public class ResponseItem {
    private String Type;
    private int X;
    private int Y;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public ResponseItem() {

    }

    public ResponseItem(String type, int x, int y) {
        Type = type;
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    @Override
    public String toString() {
        return  Type + " " + X + " " + Y;
    }
}
