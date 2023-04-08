import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Bilhetagem {
    
    private String data;
    private String linha;
    private int pagCash;
    private int pagBuComumVT;
    private int pagBuComumMens;
    private int pagEstud;
    private int pagEstudMens;
    private int pagVTMensal;
    private int pagTotal;
    private int integrBusBus;
    private int grat;
    private int gratEstud;
    private int totalPass;
    private int medPassSeg;
    private int medPassTer;
    private int medPassQua;
    private int medPassQui;
    private int medPassSex;
    private int medPassSab;
    private int medPassDom;
    private String empresa;

    public Bilhetagem(){}

    public Bilhetagem(String line){

        String[] spl = line.split(",");
        this.data = spl[0];
        this.empresa = spl[3];
        this.linha = extractCodLinha(spl[4]);
        this.pagCash = Integer.parseInt(spl[5]);
        this.pagBuComumVT = Integer.parseInt(spl[6]);
        this.pagBuComumMens = Integer.parseInt(spl[7]);
        this.pagEstud = Integer.parseInt(spl[8]);
        this.pagEstudMens = Integer.parseInt(spl[9]);
        this.pagVTMensal = Integer.parseInt(spl[10]);
        this.pagTotal = Integer.parseInt(spl[11]);
        this.integrBusBus = Integer.parseInt(spl[12]);
        this.grat = Integer.parseInt(spl[13]);
        this.gratEstud = Integer.parseInt(spl[14]);
        this.totalPass = Integer.parseInt(spl[15]);

    }

    public static String extractCodLinha(String str){

        String linha = 
            str.split("-")[0].trim();

        return linha.substring(0, 4) 
        + '-' 
        + linha.substring(4);

    }

    public static Bilhetagem generateMonthReport(List<Bilhetagem> allDays){

        int total = 0;
        int[] totalByPag = new int[10];
        Integer[][] mdByDay = new Integer[7][5];
        Calendar cal = Calendar.getInstance();
        Date data = new Date();

        for(Bilhetagem b : allDays){

            try {
                
                data = new SimpleDateFormat("dd/MM/yyyy").parse(b.getData());
                cal.setTime(data);
                if(b.getTotalPass() > 0){
                    mdByDay[cal.get(Calendar.DAY_OF_WEEK)-1][cal.get(Calendar.DAY_OF_MONTH)/7] = b.getTotalPass();
                    total += b.getTotalPass();
                    totalByPag[0] += b.getPagCash();
                    totalByPag[1] += b.getPagBuComumVT();
                    totalByPag[2] += b.getPagBuComumMens();
                    totalByPag[3] += b.getPagEstud();
                    totalByPag[4] += b.getPagEstudMens();
                    totalByPag[5] += b.getPagVTMensal();
                    totalByPag[6] += b.getPagTotal();
                    totalByPag[7] += b.getIntegrBusBus();
                    totalByPag[8] += b.getGrat();
                    totalByPag[9] += b.getGratEstud();
                }

            } catch (Exception e) {
                System.out.println(String.format("Erro bilhetagem mensal %s %s ", b.getLinha(), b.getData()));
                System.out.println(e.getMessage());
            }

        }

        Bilhetagem result = new Bilhetagem();
        result.setData(data.toString());
        result.setTotalPass(total);
        result.setMedPassDom(calcMedPassByDay(mdByDay[0]));
        result.setMedPassSeg(calcMedPassByDay(mdByDay[1]));
        result.setMedPassTer(calcMedPassByDay(mdByDay[2]));
        result.setMedPassQua(calcMedPassByDay(mdByDay[3]));
        result.setMedPassQui(calcMedPassByDay(mdByDay[4]));
        result.setMedPassSex(calcMedPassByDay(mdByDay[5]));
        result.setMedPassSab(calcMedPassByDay(mdByDay[6]));
        result.setPagCash(totalByPag[0]);
        result.setPagBuComumVT(totalByPag[1]);
        result.setPagBuComumMens(totalByPag[2]);
        result.setPagEstud(totalByPag[3]);
        result.setPagEstudMens(totalByPag[4]);
        result.setPagVTMensal(totalByPag[5]);
        result.setPagTotal(totalByPag[6]);
        result.setIntegrBusBus(totalByPag[7]);
        result.setGrat(totalByPag[8]);
        result.setGratEstud(totalByPag[9]);

        return result;

    }

    public static int calcMedPassByDay(Integer[] dayVals){

        Integer len = 0, sum = 0;
        dayVals = Arrays.stream(dayVals)
            .filter(Objects::nonNull)
            .toArray(Integer[]::new);
        Arrays.sort(dayVals);
        try{
            for(int i=1;i < dayVals.length;i++){
                if(dayVals[i] != null){
                    sum += dayVals[i];
                    len++;
                }
            }
            return sum/len;
        }catch(Exception e){
            return 0;
        }        

    }

    public String getData() {
        return data;
    }

    public String getLinha() {
        return linha;
    }

    public int getPagCash() {
        return pagCash;
    }

    public int getPagBuComumVT() {
        return pagBuComumVT;
    }

    public int getPagBuComumMens() {
        return pagBuComumMens;
    }

    public int getPagEstud() {
        return pagEstud;
    }

    public int getPagEstudMens() {
        return pagEstudMens;
    }

    public int getPagVTMensal() {
        return pagVTMensal;
    }

    public int getPagTotal() {
        return pagTotal;
    }

    public int getIntegrBusBus() {
        return integrBusBus;
    }

    public int getGrat() {
        return grat;
    }

    public int getGratEstud() {
        return gratEstud;
    }

    public int getTotalPass() {
        return totalPass;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public void setPagCash(int pagCash) {
        this.pagCash = pagCash;
    }

    public void setPagBuComumVT(int pagBuComumVT) {
        this.pagBuComumVT = pagBuComumVT;
    }

    public void setPagBuComumMens(int pagBuComumMens) {
        this.pagBuComumMens = pagBuComumMens;
    }

    public void setPagEstud(int pagEstud) {
        this.pagEstud = pagEstud;
    }

    public void setPagEstudMens(int pagEstudMens) {
        this.pagEstudMens = pagEstudMens;
    }

    public void setPagVTMensal(int pagVTMensal) {
        this.pagVTMensal = pagVTMensal;
    }

    public void setPagTotal(int pagTotal) {
        this.pagTotal = pagTotal;
    }

    public void setIntegrBusBus(int integrBusBus) {
        this.integrBusBus = integrBusBus;
    }

    public void setGrat(int grat) {
        this.grat = grat;
    }

    public void setGratEstud(int gratEstud) {
        this.gratEstud = gratEstud;
    }

    public void setTotalPass(int totalPass) {
        this.totalPass = totalPass;
    }

    public int getMedPassSeg() {
        return medPassSeg;
    }

    public void setMedPassSeg(int medPassSeg) {
        this.medPassSeg = medPassSeg;
    }

    public int getMedPassTer() {
        return medPassTer;
    }

    public void setMedPassTer(int medPassTer) {
        this.medPassTer = medPassTer;
    }

    public int getMedPassQua() {
        return medPassQua;
    }

    public void setMedPassQua(int medPassQua) {
        this.medPassQua = medPassQua;
    }

    public int getMedPassQui() {
        return medPassQui;
    }

    public void setMedPassQui(int medPassQui) {
        this.medPassQui = medPassQui;
    }

    public int getMedPassSex() {
        return medPassSex;
    }

    public void setMedPassSex(int medPassSex) {
        this.medPassSex = medPassSex;
    }

    public int getMedPassSab() {
        return medPassSab;
    }

    public void setMedPassSab(int medPassSab) {
        this.medPassSab = medPassSab;
    }

    public int getMedPassDom() {
        return medPassDom;
    }

    public void setMedPassDom(int medPassDom) {
        this.medPassDom = medPassDom;
    }

    public String getEmpresa(){
        return empresa;
    }

    



    


}
