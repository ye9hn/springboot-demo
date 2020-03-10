这是一个改造的项目（将spring boot使用jar包运行改造成使用tomcat容器运行）：
特点：
    1、pom文件中禁掉了tomcat,使用SpringBootServletInitializer的
    configure(SpringApplicationBuilder application)实现了web.xml功能
     <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
                <exclusions>
                    <exclusion>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-tomcat</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
     2、使用了自定义的dispatchservlet，使用WebApplicationInitializer接口的onStartup(ServletContext servletContext)
     实现自定义dispatchservlet
             /**
              * Spring自定义Servlet
              */
             @Component
             public class WebInitializer implements  WebApplicationInitializer{
                 @Override
                 public void onStartup(ServletContext servletContext) throws ServletException {
                     AnnotationConfigWebApplicationContext ctx=
                             new AnnotationConfigWebApplicationContext();
                     ctx.register(MyMvcConfig.class);
                     ctx.setServletContext(servletContext);
                     ServletRegistration.Dynamic servlet=servletContext.addServlet("helloServlet",new DispatcherServlet(ctx));

                     servlet.addMapping("/");
                     servlet.setLoadOnStartup(1);
                 }
             }
     3、实现自定义Servlet这样做，自己定义
             /**
              * 自定义Servlet
              */
             @Configuration
             public class ServletConfig {
                 @Autowired
                 private DispatcherServlet dispatcherServlet;
                 @Bean
                 public ServletRegistrationBean myServlet() {
                     ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
                     //自定义映射
                     bean.addUrlMappings("/");
                     bean.setName("myServlet");
                     return bean;
                 }
             }
     4、实现自定义filter或者是listener也可以按照Servlet这样做