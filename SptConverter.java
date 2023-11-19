import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SptConverter {
    
    static Calendar cal = Calendar.getInstance();
    static List<String> SHAPE_LINES;
    static List<String> BILH_LINES;
    static int MAX_READS = Integer.MAX_VALUE; //Limitar regs para testes

    public static void main(String...args){

        Map<String, Trip> trips = readAllTrips();
        Map<String, DadosMapa> dadosMapa = readDadosMapa();
        Map<String, Route> routes = readRoutes();

        //Check recent inactivated lines
        dadosMapa.values().stream()
        .filter(dm -> dm != null && trips.get(dm.getLINHA_COD()+"-0") == null && routes.get(dm.getLINHA_COD()) == null)
        .forEach(dm -> {
            System.out.println("Trip NOT found in SPTRANS file. Line will be deactivated " + dm.getLINHA_SENT());
            Trip t = deactivateLinha(dm);
            trips.put(dm.getLINHA_SENT(), t);
        });        
        
        trips.keySet().stream()
        .forEach(k -> {
            Trip t = trips.get(k);
            System.out.println("Preparing Line..." + k);
            List<Shape> tripShapes = readShapes(t.getShapeId());
            t.setShapes(tripShapes);
            DadosMapa dm = dadosMapa.get(k);
            if(dm == null){
                System.out.println("Data not found - linha " + k);
                dm = new DadosMapa();
            }

            t.setDadosMapa(dm);
            t.setRoute(routes.get(t.getCod()) != null ? routes.get(t.getCod()) : new Route());
            t.setBilhetagem(readBilhetagem(k.substring(0, 7)));
            
        });
        System.out.println("Total Lines ready to write = " + trips.size());

        writeGeoJson(trips);

    }

    public static Bilhetagem readBilhetagem(String codLinha){
        if(BILH_LINES == null){
            BILH_LINES = readFileContent(FileType.SPTRANS_BILHETAGEM);
        }
        List<Bilhetagem> allDays = new ArrayList<Bilhetagem>();
        try{
            BILH_LINES.stream()
            .filter(l -> Bilhetagem.extractCodLinha(l.split(",")[4]).equals(codLinha.trim()))
            .forEach(l -> {
                allDays.add(new Bilhetagem(l));
            }
            );
        }catch(Exception e){
            System.out.println(e.getMessage() + " ### Erro bilhetagem linha " + codLinha);
        }

        if(allDays.isEmpty()) return new Bilhetagem();

        return Bilhetagem.generateMonthReport(allDays);

    }

    private static void writeGeoJson(Map<String,Trip> trips){
        cal.setTime(new Date());
        try(FileWriter writer = new FileWriter(FileType.RESULT.getPath(), false)){

            writer.write("{\n");
            writer.write("\"type\": \"FeatureCollection\",\n");
            writer.write("\"features\": [\n");
            boolean first = true;

            for(Trip t : trips.values()){

                System.out.println("Writing line " + t.getCodSent());
                if(!first) writer.write(",\n");
                else first = false;

                writer.write("\t{\n");
                writer.write("\t\t\"type\": \"Feature\",\n");
                writer.write("\t\t\"geometry\": {\n");
                writer.write("\t\t\t\"type\": \"LineString\",\n");
                writer.write("\t\t\t\"coordinates\":[\n");
                for(int i=0;i < t.getShapes().size();i++){
                    Shape s = t.getShapes().get(i);
                    
                    if(i>0) writer.write(",\n");
                    writer.write("\t\t\t\t[");
                    writer.write(s.getLng());
                    writer.write(",");
                    writer.write(s.getLat());
                    writer.write("]");
                }
                writer.write("\t\t\t\n]\n");//coordinates
                writer.write("\t\t},\n");//geometry

                writer.write("\t\t\"properties\":{\n");
                writer.write("\t\t\t\"LINHA_SENT\" : \"" + t.getCodSent() + "\",\n");
                writer.write("\t\t\t\"LINHA_COD\" : \"" + t.getCod() + "\",\n");
                writer.write("\t\t\t\"LINHA_NOME\" : \"" + t.getRoute().getName() + "\",\n");
                writer.write("\t\t\t\"LINHA_LETREIRO\" : \"" + t.getName().toUpperCase() + "\",\n");
                writer.write("\t\t\t\"DISTANCIA\" : \"" + t.getDadosMapa().getDISTANCIA() + "\",\n");
                writer.write("\t\t\t\"DATA_CRIACAO\" : \"" + t.getDadosMapa().getDATA_CRIACAO() + "\",\n");
                writer.write("\t\t\t\"DATA_ALTERACAO\" : \"" + t.getDadosMapa().getDATA_ALTERACAO() + "\",\n");
                writer.write("\t\t\t\"DATA_DESATIVAC\" : \"" + t.getDadosMapa().getDATA_DESATIVAC() + "\",\n");
                writer.write("\t\t\t\"PRI_VIAG\" : \"" + t.getDadosMapa().getPRI_VIAG() + "\",\n");
                writer.write("\t\t\t\"ULT_VIAG\" : \"" + t.getDadosMapa().getULT_VIAG() + "\",\n");
                writer.write("\t\t\t\"COD_AREA\" : \"" + t.getDadosMapa().getCOD_AREA() + "\",\n");
                writer.write("\t\t\t\"COD_AREA_NOVO\" : \"" + t.getBilhetagem().getArea() + "\",\n");
                writer.write("\t\t\t\"TP_LINHA\" : \"" + t.getDadosMapa().getTP_LINHA() + "\",\n");
                writer.write("\t\t\t\"EMPRESA1\" : \"" + (t.getBilhetagem().getEmpresa() != null ? t.getBilhetagem().getEmpresa().toUpperCase() : t.getDadosMapa().getEMPRESA1()) + "\",\n");
                writer.write("\t\t\t\"EMPRESA2\" : \"" + t.getDadosMapa().getEMPRESA2() + "\",\n");
                writer.write("\t\t\t\"CONSORCIO\" : \"" + t.getDadosMapa().getCONSORCIO() + "\",\n");
                writer.write("\t\t\t\"SUBSISTEMA\" : \"" + t.getDadosMapa().getSUBSISTEMA() + "\",\n");
                writer.write("\t\t\t\"FROTA\" : \"" + t.getDadosMapa().getFROTA() + "\",\n");
                writer.write("\t\t\t\"DIAS\" : \"" + t.getDays() + "\",\n");
                writer.write("\t\t\t\"CLASSE\" : \"" + t.getBilhetagem().getClasse() + "\",\n");

                int tPass = t.getBilhetagem().getTotalPass();
                writer.write("\t\t\t\"PGT_DIN\" : \"" + calcPct(t.getBilhetagem().getPagCash(), tPass) + "\",\n");
                writer.write("\t\t\t\"PGT_BU_COM\" : \"" + calcPct(t.getBilhetagem().getPagBuComumVT(), tPass) + "\",\n");
                writer.write("\t\t\t\"PGT_BU_MEN\" : \"" +calcPct(t.getBilhetagem().getPagBuComumMens(), tPass) + "\",\n");
                writer.write("\t\t\t\"PGT_EST\" : \"" + calcPct(t.getBilhetagem().getPagEstud(), tPass) + "\",\n");
                writer.write("\t\t\t\"PGT_EST_MEN\" : \"" + calcPct(t.getBilhetagem().getPagEstudMens(), tPass) + "\",\n");
                writer.write("\t\t\t\"PGT_VT_MEN\" : \"" + calcPct(t.getBilhetagem().getPagVTMensal(), tPass) + "\",\n");
                writer.write("\t\t\t\"PGT_TOTAL\" : \"" + calcPct(t.getBilhetagem().getPagTotal(), tPass) + "\",\n");
                writer.write("\t\t\t\"INT_BU_BUS\" : \"" + calcPct(t.getBilhetagem().getIntegrBusBus(), tPass) + "\",\n");
                writer.write("\t\t\t\"GRT\" : \"" + calcPct(t.getBilhetagem().getGrat(), tPass) + "\",\n");
                writer.write("\t\t\t\"GRT_EST\" : \"" + calcPct(t.getBilhetagem().getGratEstud(), tPass) + "\",\n");
                writer.write("\t\t\t\"PAS_TOTAL\" : \"" + tPass + "\",\n");
                writer.write("\t\t\t\"PAS_MED_DOM\" : \"" + t.getBilhetagem().getMedPassDay(0) + "\",\n");
                writer.write("\t\t\t\"PAS_MED_SEG\" : \"" + t.getBilhetagem().getMedPassDay(1) + "\",\n");
                writer.write("\t\t\t\"PAS_MED_TER\" : \"" + t.getBilhetagem().getMedPassDay(2) + "\",\n");
                writer.write("\t\t\t\"PAS_MED_QUA\" : \"" + t.getBilhetagem().getMedPassDay(3) + "\",\n");
                writer.write("\t\t\t\"PAS_MED_QUI\" : \"" + t.getBilhetagem().getMedPassDay(4) + "\",\n");
                writer.write("\t\t\t\"PAS_MED_SEX\" : \"" + t.getBilhetagem().getMedPassDay(5) + "\",\n");
                writer.write("\t\t\t\"PAS_MED_SAB\" : \"" + t.getBilhetagem().getMedPassDay(6) + "\",\n");
                writer.write("\t\t\t\"DATA_REF\" : \"" + t.getBilhetagem().getData() + "\",\n");
                
                writer.write("\t\t\t\"OBS\" : \"" + t.getDadosMapa().getOBS() + "\"\n");
                
                writer.write("\t\t}\n");//properties

                writer.write("\t}");

            }

            writer.write("]");
            writer.write("}");

        }catch(Exception e){
            e.printStackTrace();
            System.out.println(" ### Error writing!!!");
        }
    }

    private static String calcPct(int part, int total){
        try{
            float pct = (Float.valueOf(part)*100)/Float.valueOf(total);
            return String.format("%.2f", pct) + " %";
        }catch(Exception ex){
            System.out.println("Percent calculation not possible " + ex.getMessage());
            return "";
        }
        
    }

    public static Map<String, DadosMapa> readDadosMapa(){
        List<String> lines = FileHandler.readContent(FileType.INPUT_BASE.getPath(), Integer.MAX_VALUE);
        Map<String, DadosMapa> dados = new HashMap<String, DadosMapa>();
        for(int i=0; i< lines.size();i++){
            List<String> props = new ArrayList<String>();
            String l = lines.get(i);
            if(l.contains("properties")){
                for(int j=i+1; j < lines.size();j++){

                    String l2 = lines.get(j);
                    if(l2.contains("}")) break;
                    props.add(l2);

                }
                DadosMapa d = new DadosMapa(props);
                dados.put(d.getLINHA_SENT().trim(), d);
            }
        }
        return dados;

    }

    public static List<Shape> readShapes(String shapeId){
        if(SHAPE_LINES == null){
            SHAPE_LINES = readFileContent(FileType.SPTRANS_SHAPES);
        }
        List<Shape> result = new ArrayList<Shape>();
        SHAPE_LINES.stream()
        .filter(l -> l.split(",")[0].equals(shapeId))
        .forEach(l -> result.add(new Shape(l)));

        return result;

    }

    public static Map<String, Route> readRoutes(){
        List<String> routes = readFileContent(FileType.SPTRANS_ROUTES);
        Map<String, Route> result = new HashMap<String, Route>();
        routes.stream().forEach(l -> {
            Route r = new Route(l);
            result.put(r.getRouteId(), r);
        });

        return result;

    }

    public static Map<String, Trip> readAllTrips(){
        List<String> trips = FileHandler.readContent(FileType.SPTRANS_TRIPS.getPath(), MAX_READS);
        Map<String, Trip> result = new HashMap<String, Trip>();
        trips.stream().forEach(t -> {
            Trip trip = new Trip(t);
            result.put(trip.getCodSent().trim(), trip);
        });

        return result;

    }

    public static List<String> readFileContent(FileType file){
        return FileHandler.readContent(file.getPath(), Integer.MAX_VALUE);
    }

    private static Trip deactivateLinha(DadosMapa dm){

        cal.setTime(new Date());
        dm.setDATA_DESATIVAC(String.valueOf(cal.get(Calendar.YEAR)));
        Trip t = new Trip(
            dm.getLINHA_COD(),
            dm.getLINHA_SENT(),
            dm.getLINHA_NOME()
        );
        t.setDadosMapa(dm);
        t.setBilhetagem(new Bilhetagem());

        return t;

    }

}
