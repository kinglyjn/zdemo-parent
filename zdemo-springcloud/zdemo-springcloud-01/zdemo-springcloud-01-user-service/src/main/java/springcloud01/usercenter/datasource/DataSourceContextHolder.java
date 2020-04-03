package springcloud01.usercenter.datasource;

/**
 * @Author KJ
 * @Date 2020-04-01 5:50 PM
 * @Description
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();

    /**
     * 设置数据源
     * @param db
     */
    public static void setDataSource(String db){
        contextHolder.set(db);
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDataSource(){
        return contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clear(){
        contextHolder.remove();
    }

}
