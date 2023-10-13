/**
 * <h3>druid 配置多数据源</h3>
 *
 * <h4>使用说明：要修改以下几个地方</h4>
 * <pre>
 *     1. 配置 application.xml 文件
 *     2. DataSourceType 枚举类
 *     3. DruidConfiguration 类
 *     --------------------------------------------
 *     有异常可以声明以下，程序正常就不用声明
 *     @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
 * </pre>
 * <h4>1. 配置 application.xml 文件</h4>
 * <pre>
 *      在 spring.datasource.druid 下增加数据源 如下 配置
 *      # 增加数据源 db-1
 *      db-1:
 *         enabled: false
 *         url: xxxxx # 填写相应的数据
 *         username: xxxxx # 填写相应的数据
 *         password: xxxxx # 填写相应的数据
 *
 * </pre>
 * <h4>2. DataSourceType 枚举类</h4>
 * <pre>
 *     在相应的 DataSourceType 类下添加枚举字段, 如下
 *     public enum DataSourceType {
 *          ...,
 *          // 与新增的配置文件上的数据源名一致
 *          DB-1,
 *     }
 *
 * </pre>
 * <h4>3. DruidConfiguration 类</h4>
 * <pre>
 *     在 DruidConfiguration 创建相应的 datasource 源
 *     @Bean
 *     @ConfigurationProperties("spring.datasource.druid.db-1")
 *     public DataSource db1DataSource(DruidProperties druidProperties) {
 *         DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
 *         return druidProperties.dataSource(dataSource);
 *     }
 *
 *     并在 方法中增加一个参数
 *     @Bean(name = "dynamicDataSource")
 *     @Primary
 *     public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource,
 *                              DataSource db1DataSource,...) {
 *         Map<Object, Object> targetDataSources = new HashMap<>();
 *         targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
 *         targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
 *         targetDataSources.put(DataSourceType.DB1.name(), db1DataSource);
 *         ...
 *         return new DynamicDataSource(masterDataSource, targetDataSources);
 *     }
 * </pre>
 *
 * <pre>
 * @author <a href="http://www.peyton.cn">peyton</a>
 * @mail <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @date 2023年10月04日 20:11:31
 * @version 1.0.0
 * </pre>
 */
package cn.peyton.plum.core.driud;
/**
 * <pre>
 *     用法: 直接在 service 实现层 类 或 方法 上 声明
 *     @DataSource(value=DataSourceType.xxx)
 *    不声明就默认 value=DataSourceType.MASTER
 * </pre>
 */
