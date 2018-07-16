package datasource.domain;

/**
 * Created by puroc on 16/12/14.
 */
public class CabinetData {

    private String temperature;
    private String availableSpace;
    private String availableRate;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(String availableSpace) {
        this.availableSpace = availableSpace;
    }

    public String getAvailableRate() {
        return availableRate;
    }

    public void setAvailableRate(String availableRate) {
        this.availableRate = availableRate;
    }
}
