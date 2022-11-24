public class Shape {
    
    private String shapeId;
    private String lat;
    private String lng;
    private Integer seq;
    private String distance;

    public Shape(String line){

        String[] spl = line.split(",");
        this.shapeId = spl[0];
        this.lat = spl[1];
        this.lng = spl[2];
        this.seq = Integer.parseInt(spl[3]);
        this.distance = spl[4];

    }

    public String getShapeId() {
        return shapeId;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public Integer getSeq() {
        return seq;
    }

    public String getDistance() {
        return distance;
    }

    

}
