public class ResponseItem {
    private String Type;
    private double[][] normalized;
    private double[] proximities;
    private int resultClass;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public double[][] getNormalized() {
        return normalized;
    }

    public void setNormalized(double[][] normalized) {
        this.normalized = normalized;
    }

    public double[] getProximities() {
        return proximities;
    }

    public void setProximities(double[] proximities) {
        this.proximities = proximities;
    }

    public int getResultClass() {
        return resultClass;
    }

    public void setResultClass(int resultClass) {
        this.resultClass = resultClass;
    }
}
