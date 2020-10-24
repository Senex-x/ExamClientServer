class ResponseItem {
    private int Type;
    private int X;
    private int Y;

    int getX() {
        return X;
    }

    void setX(int x) {
        this.X = x;
    }

    int getY() {
        return Y;
    }

    void setY(int y) {
        this.Y = y;
    }

    int getMessageType() {
        return Type;
    }

    void setMessageType(int messageType) {
        this.Type = messageType;
    }

    @Override
    public String toString() {
        return  "Message type is: " + Type + ", first value is: " + X + ", second value is: " + Y;
    }
}
