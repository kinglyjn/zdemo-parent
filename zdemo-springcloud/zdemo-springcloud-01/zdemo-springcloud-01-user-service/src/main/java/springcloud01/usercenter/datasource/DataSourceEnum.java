package springcloud01.usercenter.datasource;

/**
 * @Author KJ
 * @Date 2020-04-01 5:51 PM
 * @Description
 */
public enum DataSourceEnum {
    DB1("db1"), DB2("db2");

    private String value;
    DataSourceEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
