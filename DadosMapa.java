import java.lang.reflect.Field;
import java.util.List;

public class DadosMapa {
    
    private String LINHA_SENT;
    private String LINHA_COD;
    private String LINHA_NOME;
    private String LINHA_LETREIRO;
    private String DATA_CRIACAO;
    private String DATA_DESATIVAC;
    private String DATA_ALTERACAO;
    private String DATA_REF;
    private String DISTANCIA;
    private String PRI_VIAG;
    private String ULT_VIAG;
    private String COD_AREA;
    private String TP_LINHA;
    private String EMPRESA1;
    private String EMPRESA2;
    private String CONSORCIO;
    private String SUBSISTEMA;
    private String FROTA;
    private String DIAS;
    private String CLASSE;
    private String PGT_DIN;
    private String PGT_BU_COM;
    private String PGT_BU_MEN;
    private String PGT_EST;
    private String PGT_EST_MEN;
    private String PGT_VT_MEN;
    private String PGT_TOTAL;
    private String INT_BU_BUS;
    private String GRT;
    private String GRT_EST;
    private String PAS_TOTAL;
    private String PAS_MED_DOM;
    private String PAS_MED_SEG;
    private String PAS_MED_TER;    
    private String PAS_MED_QUA;
    private String PAS_MED_QUI;
    private String PAS_MED_SEX;
    private String PAS_MED_SAB;
    private String ATIVA;
    private String OBS;  

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
                System.err.println("ERRO " + e.getMessage());
            }
            
        }

    }

    public String getLINHA_SENT() {
        return LINHA_SENT;
    }

    public String getLINHA_COD() {
        return LINHA_COD;
    }

    public String getLINHA_NOME() {
        return LINHA_NOME;
    }

    public String getLINHA_LETREIRO() {
        return LINHA_LETREIRO;
    }

    public String getDATA_CRIACAO() {
        return DATA_CRIACAO;
    }

    public String getDATA_DESATIVAC() {
        return DATA_DESATIVAC;
    }

    public String getDATA_ALTERACAO() {
        return DATA_ALTERACAO;
    }

    public String getDATA_REF() {
        return DATA_REF;
    }

    public String getDISTANCIA() {
        return DISTANCIA;
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

    public String getDIAS() {
        return DIAS;
    }

    public String getCLASSE() {
        return CLASSE;
    }

    public String getPGT_DIN() {
        return PGT_DIN;
    }

    public String getPGT_BU_COM() {
        return PGT_BU_COM;
    }

    public String getPGT_BU_MEN() {
        return PGT_BU_MEN;
    }

    public String getPGT_EST() {
        return PGT_EST;
    }

    public String getPGT_EST_MEN() {
        return PGT_EST_MEN;
    }

    public String getPGT_VT_MEN() {
        return PGT_VT_MEN;
    }

    public String getPGT_TOTAL() {
        return PGT_TOTAL;
    }

    public String getINT_BU_BUS() {
        return INT_BU_BUS;
    }

    public String getGRT() {
        return GRT;
    }

    public String getGRT_EST() {
        return GRT_EST;
    }

    public String getPAS_TOTAL() {
        return PAS_TOTAL;
    }

    public String getPAS_MED_DOM() {
        return PAS_MED_DOM;
    }

    public String getPAS_MED_SEG() {
        return PAS_MED_SEG;
    }

    public String getPAS_MED_TER() {
        return PAS_MED_TER;
    }

    public String getPAS_MED_QUA() {
        return PAS_MED_QUA;
    }

    public String getPAS_MED_QUI() {
        return PAS_MED_QUI;
    }

    public String getPAS_MED_SEX() {
        return PAS_MED_SEX;
    }

    public String getPAS_MED_SAB() {
        return PAS_MED_SAB;
    }

    public String getATIVA() {
        return ATIVA;
    }

    public String getOBS() {
        return OBS;
    }

    public void setDATA_DESATIVAC(String dATA_DESATIVAC) {
        DATA_DESATIVAC = dATA_DESATIVAC;
    }

    public void setATIVA(String aTIVA) {
        ATIVA = aTIVA;
    }
 

}

