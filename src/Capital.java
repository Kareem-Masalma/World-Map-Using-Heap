public class Capital {
    private String capitalName;
    private double latitude;
    private double longitude;
    private double x;
    private double y;

    public Capital(String capitalName, double latitude, double longitude, double x, double y) {
        this.capitalName = capitalName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.x = x;
        this.y = y;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    private void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    private void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    private void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Capital{" +
                "capitalName='" + capitalName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}