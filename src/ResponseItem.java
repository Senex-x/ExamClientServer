public class ResponseItem {
    private int Type;
    private int X;
    private int Y;

    public ResponseItem() {

    }

    public ResponseItem(int messageType, int x, int y) {
        this.Type = messageType;
        this.X = x;
        this.Y = y;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        this.X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public int getMessageType() {
        return Type;
    }

    public void setMessageType(int messageType) {
        this.Type = messageType;
    }

    @Override
    public String toString() {
        return  "Message type is: " + Type + ", first value is: " + X + ", second value is: " + Y;
    }
}
