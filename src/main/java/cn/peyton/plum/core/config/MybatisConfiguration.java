package cn.peyton.plum.core.config;

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * <h4>配置 mybatis</h4>
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @email <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2023年10月04日 20:45
 * @version 1.0.0
 * </pre>
 */
@MapperScan("cn.peyton.**.mapper")
@Configuration
public class MybatisConfiguration implements Serializable {
    private static final long serialVersionUID = 1L;

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                //开启驼峰命名映射
                configuration.setMapUnderscoreToCamelCase(true);
                //默认语句超时时间
                configuration.setDefaultStatementTimeout(25);
                //默认提取大小
                configuration.setDefaultFetchSize(100);
                //使用redis 数据缓存 这个要设置为false
                //全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载。
                configuration.setLazyLoadingEnabled(false);
                //当启用时，有延迟加载属性的对象在被调用时将会完全加载任意属性。否则，每种属性将会按需要加载。
                configuration.setAggressiveLazyLoading(true);
                //是否允许单条sql 返回多个数据集  (取决于驱动的兼容性) default:true
                configuration.setMultipleResultSetsEnabled(true);
                //是否可以使用列的别名 (取决于驱动的兼容性) default:true
                configuration.setUseColumnLabel(true);
                //允许JDBC 生成主键。需要驱动器支持。
                // 如果设为了true，这个设置将强制使用被生成的主键，有一些驱动器不兼容不过仍然可以执行。  default:false
                configuration.setUseGeneratedKeys(true);
                //指定 MyBatis 如何自动映射 数据基表的列 NONE：不隐射　PARTIAL:部分  FULL:全部
                configuration.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
                //这是默认的执行类型
                // 1、SIMPLE: 简单；
                // 2、REUSE: 执行器可能重复使用prepared statements语句；
                // 3、BATCH: 执行器可以重复执行语句和批量更新；
                configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
                //
                configuration.setSafeRowBoundsEnabled(false);
                //设置本地缓存范围 session:就会有数据的共享  statement:语句范围 (这样就不会有数据的共享 ) defalut:session
                configuration.setLocalCacheScope(LocalCacheScope.SESSION);
                //默认为OTHER,为了解决oracle插入null报错的问题要设置为NULL
                configuration.setJdbcTypeForNull(JdbcType.NULL);

            }
        };
    }
}
