public class Route {
    
    private String routeId;
    private String name;
    private String routeColor;

    public Route(){}

    public Route(String line){

        String[] spl = line.split(",");
        this.routeId = spl[0];
        this.name = spl[3];
        this.routeColor = spl[5];

    }

    public String getName() {
        return name;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public String getRouteId() {
        return routeId;
    }

    

}
