package xin.spring.bless.fast.factory;

/**
 * 描述，BuilderBeanFactory bean工厂
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class BuilderBeanFactory {

    public BuilderBeanFactory(){}

    public static BuilderBeanFactory get(){
        return new BuilderBeanFactory();
    }

    /**
     * 方法描述： 建造bean
     * @param cla 类型
     * @param <T> 实例
     * @return
     */
    public <T> T build(Class<T> cla) {
        T t = null;
        try {
            t = cla.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

}
