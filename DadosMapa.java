import java.lang.reflect.Field;
import java.util.List;

public class DadosMapa {
    
    private String CODIGO;
    private String SENTIDO;
    private String LINHA_CODI;
    private String DATA_CRIA;
    private String PRI_VIAG;
    private String ULT_VIAG;
    private String COD_AREA;
    private String TP_LINHA;
    private String EMPRESA1;
    private String EMPRESA2;
    private String CONSORCIO;
    private String SUBSISTEMA;
    private String FROTA;
    private String CLASSE;
    private String PASS_DIN;
    private String PASS_BCOM;
    private String PASS_BMENS;
    private String PASS_BEST;
    private String PASS_BVT;
    private String PASS_BVTME;
    private String PASS_INMC;
    private String PASS_INMCM;
    private String PASS_PAGA;
    private String PASS_INBUS;
    private String PASS_GRAT;
    private String TOTAL_PASS;
    private String DATA_ATUALIZA;
    private String DATA_DESATIVA;    

    public DadosMapa(){}

    public DadosMapa(List<String> lines){

        for(int j=0; j < lines.size(); j++){

            try{
                String l2 = lines.get(j).replace(",", "");
                for(Field f : this.getClass().getDeclaredFields()){

                    if(l2.contains(f.getName())){
                        String[] spl = l2.split(":");
                        String val = spl[1];
                        if(spl.length > 2) val = val + ":" + spl[2];
                        f.set(this, val);
                        break;
                    }

                }
            }catch(Exception e){
                System.err.println("Ops " + e.getMessage());
            }
            
        }
        Integer sentido = Integer.parseInt(this.SENTIDO.trim()) - 1;
        this.LINHA_CODI = this.CODIGO.trim().concat("-" + sentido);

    }

    public String getLINHA_CODI() {
        return LINHA_CODI;
    }

    public String getDATA_CRIA() {
        return DATA_CRIA;
    }

    public String getPRI_VIAG() {
        return PRI_VIAG;
    }

    public String getULT_VIAG() {
        return ULT_VIAG;
    }

    public String getCOD_AREA() {
        return COD_AREA;
    }

    public String getTP_LINHA() {
        return TP_LINHA;
    }

    public String getEMPRESA1() {
        return EMPRESA1;
    }

    public String getEMPRESA2() {
        return EMPRESA2;
    }

    public String getCONSORCIO() {
        return CONSORCIO;
    }

    public String getSUBSISTEMA() {
        return SUBSISTEMA;
    }

    public String getFROTA() {
        return FROTA;
    }

    public String getCLASSE() {
        return CLASSE;
    }

    public String getPASS_DIN() {
        return PASS_DIN;
    }

    public String getPASS_BCOM() {
        return PASS_BCOM;
    }

    public String getPASS_BMENS() {
        return PASS_BMENS;
    }

    public String getPASS_BEST() {
        return PASS_BEST;
    }

    public String getPASS_BVT() {
        return PASS_BVT;
    }

    public String getPASS_BVTME() {
        return PASS_BVTME;
    }

    public String getPASS_INMC() {
        return PASS_INMC;
    }

    public String getPASS_INMCM() {
        return PASS_INMCM;
    }

    public String getPASS_PAGA() {
        return PASS_PAGA;
    }

    public String getPASS_INBUS() {
        return PASS_INBUS;
    }

    public String getPASS_GRAT() {
        return PASS_GRAT;
    }

    public String getTOTAL_PASS() {
        return TOTAL_PASS;
    }

    public String getDATA_ATUALIZA() {
        return DATA_ATUALIZA;
    }

    public String getDATA_DESATIVA() {
        return DATA_DESATIVA;
    }

    public void setLINHA_CODI(String lINHA_CODI) {
        LINHA_CODI = lINHA_CODI;
    }

    public void setDATA_CRIA(String dATA_CRIA) {
        DATA_CRIA = dATA_CRIA;
    }

    public void setPRI_VIAG(String pRI_VIAG) {
        PRI_VIAG = pRI_VIAG;
    }

    public void setULT_VIAG(String uLT_VIAG) {
        ULT_VIAG = uLT_VIAG;
    }

    public void setCOD_AREA(String cOD_AREA) {
        COD_AREA = cOD_AREA;
    }

    public void setTP_LINHA(String tP_LINHA) {
        TP_LINHA = tP_LINHA;
    }

    public void setEMPRESA1(String eMPRESA1) {
        EMPRESA1 = eMPRESA1;
    }

    public void setEMPRESA2(String eMPRESA2) {
        EMPRESA2 = eMPRESA2;
    }

    public void setCONSORCIO(String cONSORCIO) {
        CONSORCIO = cONSORCIO;
    }

    public void setSUBSISTEMA(String sUBSISTEMA) {
        SUBSISTEMA = sUBSISTEMA;
    }

    public void setFROTA(String fROTA) {
        FROTA = fROTA;
    }

    public void setCLASSE(String cLASSE) {
        CLASSE = cLASSE;
    }

    public void setPASS_DIN(String pASS_DIN) {
        PASS_DIN = pASS_DIN;
    }

    public void setPASS_BCOM(String pASS_BCOM) {
        PASS_BCOM = pASS_BCOM;
    }

    public void setPASS_BMENS(String pASS_BMENS) {
        PASS_BMENS = pASS_BMENS;
    }

    public void setPASS_BEST(String pASS_BEST) {
        PASS_BEST = pASS_BEST;
    }

    public void setPASS_BVT(String pASS_BVT) {
        PASS_BVT = pASS_BVT;
    }

    public void setPASS_BVTME(String pASS_BVTME) {
        PASS_BVTME = pASS_BVTME;
    }

    public void setPASS_INMC(String pASS_INMC) {
        PASS_INMC = pASS_INMC;
    }

    public void setPASS_INMCM(String pASS_INMCM) {
        PASS_INMCM = pASS_INMCM;
    }

    public void setPASS_PAGA(String pASS_PAGA) {
        PASS_PAGA = pASS_PAGA;
    }

    public void setPASS_INBUS(String pASS_INBUS) {
        PASS_INBUS = pASS_INBUS;
    }

    public void setPASS_GRAT(String pASS_GRAT) {
        PASS_GRAT = pASS_GRAT;
    }

    public void setTOTAL_PASS(String tOTAL_PASS) {
        TOTAL_PASS = tOTAL_PASS;
    }

    public void setDATA_ATUALIZA(String dATA_ATUALIZA) {
        DATA_ATUALIZA = dATA_ATUALIZA;
    }

    public void setDATA_DESATIVA(String dATA_DESATIVA) {
        DATA_DESATIVA = dATA_DESATIVA;
    }

    

}

