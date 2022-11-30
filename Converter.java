import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converter {
    
    static List<String> SHAPE_LINES;
    static List<String> BILH_LINES;
    static String BILH_FILE_PATH = "files/bilhetagem/09-Setembro-2022.csv";
    static String OUTPUT_FILE_PATH = "files/output/new_geofile.json";
    static int MAX_READS = Integer.MAX_VALUE; //Limitar regs para testes

    public static void main(String...args){

        Map<String, Trip> trips = readAllTrips();
        Map<String, DadosMapa> dadosMapa = readDadosMapa();
        Map<String, Route> routes = readRoutes();

        dadosMapa.keySet().forEach(k -> System.out.println(k));

        //Check recent inactivated lines
        dadosMapa.keySet().stream()
        .filter(k -> k != null && trips.get(k) == null)
        .forEach(k -> {
            System.out.println("Trips NOT found. Line will be deactivated " + k);
            DadosMapa dm = dadosMapa.get(k);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            dm.setDATA_DESATIVAC(String.valueOf(cal.get(Calendar.YEAR)));
            Trip t = new Trip(
                dm.getLINHA_COD(),
                dm.getLINHA_SENT(),
                dm.getLINHA_NOME()
            );
            t.setDadosMapa(dm);
            t.setBilhetagem(new Bilhetagem());
            trips.put(k, t);
        });

        
        trips.keySet().stream()
        .forEach(k -> {
            Trip t = trips.get(k);
            System.out.println("Preparing Line..." + k);
            List<Shape> tripShapes = readShapes(t.getShapeId());
            t.setShapes(tripShapes);
            DadosMapa dm = dadosMapa.get(k);

            t.setDadosMapa(dm != null ? dm : new DadosMapa());
            t.setRoute(routes.get(k) != null ? routes.get(k) : new Route());
            t.setBilhetagem(readBilhetagem(k.substring(0, 7)));
            
        });
        System.out.println("Total Lines ready " + trips.size());

        writeGeoJson(trips);

    }

    public static Bilhetagem readBilhetagem(String codLinha){
        if(BILH_LINES == null){
            BILH_LINES = readFileContent(BILH_FILE_PATH);
        }
        List<Bilhetagem> allDays = new ArrayList<Bilhetagem>();
        BILH_LINES.stream()
        .filter(l -> Bilhetagem.extractCodLinha(l.split(",")[4]).equals(codLinha.trim()))
        .forEach(l ->{
            allDays.add(new Bilhetagem(l));
        });

        if(allDays.isEmpty()) return new Bilhetagem();

        return Bilhetagem.generateMonthReport(allDays);

    }

    public static void writeGeoJson(Map<String,Trip> trips){
        System.out.println("Writing geojson...");
        try(FileWriter writer = new FileWriter(OUTPUT_FILE_PATH, false)){

            writer.write("{\n");
            writer.write("\"type\": \"FeatureCollection\",\n");
            writer.write("\"features\": [\n");
            boolean first = true;

            for(Trip t : trips.values()){

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
                writer.write("\t\t\t\"TP_LINHA\" : \"" + t.getDadosMapa().getTP_LINHA() + "\",\n");
                writer.write("\t\t\t\"EMPRESA1\" : \"" + t.getDadosMapa().getEMPRESA1() + "\",\n");
                writer.write("\t\t\t\"EMPRESA2\" : \"" + t.getDadosMapa().getEMPRESA2() + "\",\n");
                writer.write("\t\t\t\"CONSORCIO\" : \"" + t.getDadosMapa().getCONSORCIO() + "\",\n");
                writer.write("\t\t\t\"SUBSISTEMA\" : \"" + t.getDadosMapa().getSUBSISTEMA() + "\",\n");
                writer.write("\t\t\t\"FROTA\" : \"" + t.getDadosMapa().getFROTA() + "\",\n");
                writer.write("\t\t\t\"DIAS\" : \"" + t.getDays() + "\",\n");
                writer.write("\t\t\t\"CLASSE\" : \"" + t.getDadosMapa().getCLASSE() + "\",\n");

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
                writer.write("\t\t\t\"PAS_MED_DOM\" : \"" + t.getBilhetagem().getMedPassDom() + "\",\n");
                writer.write("\t\t\t\"PAS_MED_SEG\" : \"" + t.getBilhetagem().getMedPassSeg() + "\",\n");
                writer.write("\t\t\t\"PAS_MED_TER\" : \"" + t.getBilhetagem().getMedPassTer() + "\",\n");
                writer.write("\t\t\t\"PAS_MED_QUA\" : \"" + t.getBilhetagem().getMedPassQua() + "\",\n");
                writer.write("\t\t\t\"PAS_MED_QUI\" : \"" + t.getBilhetagem().getMedPassQui() + "\",\n");
                writer.write("\t\t\t\"PAS_MED_SEX\" : \"" + t.getBilhetagem().getMedPassSex() + "\",\n");
                writer.write("\t\t\t\"PAS_MED_SAB\" : \"" + t.getBilhetagem().getMedPassSab() + "\",\n");
                writer.write("\t\t\t\"DATA_REF\" : \"" + t.getBilhetagem().getData() + "\",\n");
                String dataDesativac = t.getDadosMapa().getDATA_DESATIVAC();
                writer.write("\t\t\t\"ATIVA\" : \"" + (dataDesativac == null || dataDesativac.trim() == "" ? 1 : 0) + "\",\n");
                writer.write("\t\t\t\"OBS\" : \"" + t.getDadosMapa().getOBS() + "\"\n");
                
                writer.write("\t\t}\n");//properties

                writer.write("\t}");

            }

            writer.write("]");
            writer.write("}");

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    private static String calcPct(int part, int total){
        try{
            float pct = (Float.valueOf(part)*100)/Float.valueOf(total);
            return String.format("%.2f", pct) + " %";
        }catch(Exception ex){
            return "";
        }
    }

    public static Map<String, DadosMapa> readDadosMapa(){
        List<String> lines = readFileContent("files/input/LB15_LI_MSP_CEM_v4.json", MAX_READS);
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
            SHAPE_LINES = readFileContent("files/sptrans-gtfs/shapes.txt");
        }
        List<Shape> result = new ArrayList<Shape>();
        SHAPE_LINES.stream()
        .filter(l -> l.split(",")[0].equals(shapeId))
        .forEach(l -> result.add(new Shape(l)));

        return result;

    }

    public static Map<String, Route> readRoutes(){
        List<String> routes = readFileContent("files/sptrans-gtfs/routes.txt");
        Map<String, Route> result = new HashMap<String, Route>();
        routes.stream().forEach(l -> {
            Route r = new Route(l);
            result.put(r.getRouteId() + "-0", r);
            result.put(r.getRouteId() + "-1", r);
        });

        return result;

    }

    public static Map<String, Trip> readAllTrips(){
        List<String> trips = readFileContent("files/sptrans-gtfs/trips.txt");
        Map<String, Trip> result = new HashMap<String, Trip>();
        trips.stream().forEach(t -> {
            Trip trip = new Trip(t);
            result.put(trip.getCodSent().trim(), trip);
        });

        return result;

    }

    public static List<String> readFileContent(String name){
        return readFileContent(name, Integer.MAX_VALUE);
    }

    public static List<String> readFileContent(String name, int limit){

        List<String> result = new ArrayList<String>();

        try(BufferedReader reader = new BufferedReader
        (new InputStreamReader(new FileInputStream(name), "ISO-8859-1"))){
            
            reader.lines()
            .skip(1)
            .limit(limit)
            .forEach(l -> result.add(l.replace("\"", "")));

        }catch(Exception e){
            System.err.println(e.getMessage());
        }   

        System.out.println(name + " -> Lines read " + result.size());

        return result;

    }

}
