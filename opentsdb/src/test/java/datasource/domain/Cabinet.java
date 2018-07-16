package datasource.domain;

public class Cabinet {
    private String deviceId;

    private String deviceType;

    private String createTime;

    private CabinetData data;

    public CabinetData getData() {
        return data;
    }

    public void setData(CabinetData data) {
        this.data = data;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}