SpringBoot 整合 Filter （都用到了MyFilter类）
第一种 用注解方式
    1、在 WebFilter类头部的添加 
        @WebServlet(name = "WebFilter",urlPatterns = "/test")
    2、在启动类头部添加Servlet扫描注解
        @ServletComponentScan


第二种 方法
    直接在启动类中添加这个方法,不用在任何地方用注解
    @Bean  //注册Servlet
    public ServletRegistrationBean getServletRegistrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new MyServlet());
        bean.addUrlMappings("/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean getFilterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new MyFilter());
        bean.addUrlPatterns(new String[]{"*.do","*.jsp"});
        bean.addUrlPatterns("/test");
        return bean;
    }