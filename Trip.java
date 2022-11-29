import java.util.List;

public class Trip {
    
    private String cod;
    private String days;
    private String codSent;
    private String name;
    private String shapeId;
    private Route route;
    private DadosMapa dadosMapa;
    private Bilhetagem bilhetagem;
    private List<Shape> shapes;

    public Trip(String cod, String codSent, String name){
        
        this.cod = cod;
        this.codSent = codSent;
        this.name = name;
        this.days = "";
        
    }

    public Trip(String line){

        String[] spl = line.split(",");
        this.cod = spl[0];
        this.days = spl[1];
        this.codSent = spl[2];
        this.name = spl[3];
        this.shapeId = spl[5];

    }

    public String getCod() {
        return cod;
    }

    public String getDays() {
        return days;
    }

    public String getCodSent() {
        return codSent;
    }

    public String getName() {
        return name;
    }

    public String getShapeId() {
        return shapeId;
    }
    

    public DadosMapa getDadosMapa() {
        return dadosMapa;
    }

    public void setDadosMapa(DadosMapa dadosMapa) {
        this.dadosMapa = dadosMapa;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Bilhetagem getBilhetagem() {
        return bilhetagem;
    }

    public void setBilhetagem(Bilhetagem bilhetagem) {
        this.bilhetagem = bilhetagem;
    }    

    

}
