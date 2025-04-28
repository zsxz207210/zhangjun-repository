package pojo;

public class riskFactor {
    private String type;
    private String time;
    private String batchNumber;
    private String kuozengzi;
    private String xulie;

    public riskFactor(String type, String time, String batchNumber, String kuozengzi, String xulie) {
        this.type = type;
        this.time = time;
        this.batchNumber = batchNumber;
        this.kuozengzi = kuozengzi;
        this.xulie = xulie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getKuozengzi() {
        return kuozengzi;
    }

    public void setKuozengzi(String kuozengzi) {
        this.kuozengzi = kuozengzi;
    }

    public String getXulie() {
        return xulie;
    }

    public void setXulie(String xulie) {
        this.xulie = xulie;
    }

    @Override
    public String toString() {
        return "riskFactor{" +
                "type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", kuozengzi='" + kuozengzi + '\'' +
                ", xulie='" + xulie + '\'' +
                '}';
    }
}
