package com.wraith.configuration;

import com.wraith.ApplicationConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class RestExporterWebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {


        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));

//        FilterRegistration.Dynamic filter = servletContext.addFilter("springSecurityFilterChain", "org.springframework.web.filter.DelegatingFilterProxy");
//        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webCtx);
        ServletRegistration.Dynamic reg = servletContext.addServlet("rest-exporter", dispatcherServlet);
        reg.setLoadOnStartup(1);
        reg.addMapping("/*");
    }
}

