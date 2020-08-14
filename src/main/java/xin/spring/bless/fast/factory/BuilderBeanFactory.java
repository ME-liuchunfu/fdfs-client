package xin.spring.bless.fast.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述，BuilderBeanFactory bean工厂
 * 作者： liuchunfu
 * 时间：2020-08-03 16:11
 */
public class BuilderBeanFactory {

    protected static Logger logger = LoggerFactory.getLogger(BuilderBeanFactory.class);

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
        logger.info("建造：{}", cla.getName());
        T t = null;
        try {
            t = cla.newInstance();
            logger.info("建造成功：{}", t.hashCode());
        } catch (InstantiationException e) {
            logger.info("建造失败：{}", e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.info("建造失败：{}", e.getMessage());
            e.printStackTrace();
        }
        return t;
    }

}
