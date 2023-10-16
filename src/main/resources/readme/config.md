
1. 驱动类
    MySQL5.x的版本使用的驱动类是com.mysql.jdbc.Driver
    MySQL8.x的版本使用的驱动类是com.mysql.cj.jdbc.Driver
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
   </dependency>

2. Druid 数据库连接池 {DBCP、C3P0、BoneCP、Proxool、JBossDataSource}

    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>1.1.9</version>
    </dependency>

3. 集成Mybatis
   <dependency>
       <groupId>org.mybatis.spring.boot</groupId>
       <artifactId>mybatis-spring-boot-starter</artifactId>
       <version>2.0.1</version>
   </dependency>
   
4. redis 数据库
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
   </dependency>

    修改 protected-mode  yes 改为：protected-mode no
    默认为不守护进程模式，把daemonize no 改为daemonize yes
    注释掉 #bind 127.0.0.1 ，外网连接可设置 bind 0.0.0.0

   配置:
   spring:
     redis:
       host: 127.0.0.1
       # Redis服务器端口号
       port: 6379
       # 使用的数据库索引，默认是0
       database: 0
       # 连接超时时间
       timeout: 1800000
       # 设置密码
       password: "hc2919"
       lettuce:
         pool:
         # 最大阻塞等待时间，负数表示没有限制
         max-wait: -1
         # 连接池中的最大空闲连接
         max-idle: 5
         # 连接池中的最小空闲连接
         min-idle: 0
         # 连接池中最大连接数，负数表示没有限制
         max-active: 20

5. 666





