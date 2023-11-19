public enum FileType{
    RESULT("files/output/geofile.json"),
    INPUT_BASE("files/input/base.json"),
    SPTRANS_BILHETAGEM("files/input/sptrans/bilhetagem.csv"),
    SPTRANS_SHAPES("files/input/sptrans/shapes.txt"),
    SPTRANS_ROUTES("files/input/sptrans/routes.txt"),
    SPTRANS_TRIPS("files/input/sptrans/trips.txt");

    private String path;

    FileType(String path){
        this.path = path;
    }

    public String getPath(){
        /*
        if(this.path.contains("output")){
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            String dateStr = cal.get(Calendar.YEAR) + "_" + cal.get(Calendar.MONTH) + "_" + cal.get(Calendar.MILLISECOND);
            String[] splitpath = this.path.split(".");
            if(splitpath.length > 1) return splitpath[0] + dateStr + "." + splitpath[1];
            else return dateStr + ".json";
        }
        */
        return this.path;
    }

}