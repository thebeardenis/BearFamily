package org.repository.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

@Configuration
@ComponentScan("org.repository")
@EnableWebMvc
public class MainConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
    private final File PROPS = new File("C:\\Users\\denis\\IdeaProjects\\bearSite\\src\\main\\java\\org\\repository\\config\\config.properties");

    @Autowired
    public MainConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {

        SpringResourceTemplateResolver tr = new SpringResourceTemplateResolver();
        tr.setApplicationContext(applicationContext);
        tr.setPrefix("classpath:/static/pages/");
        tr.setCharacterEncoding("UTF-8");
        tr.setCheckExistence(true);

        return tr;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/styles/**")
                .addResourceLocations("classpath:/static/source/styles/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/source/img/");
        registry.addResourceHandler("/files/**")
                .addResourceLocations("classpath:/static/source/files/");
        registry.addResourceHandler("/favicon/**")
                .addResourceLocations("classpath:/static/source/favicon/");
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dm = new DriverManagerDataSource();
        dm.setDriverClassName("org.postgresql.Driver");
        try (InputStream is = Files.newInputStream(PROPS.toPath())) {
            Properties properties = new Properties();
            properties.load(is);
            dm.setUsername(properties.getProperty("db_name"));
            dm.setPassword(properties.getProperty("db_password"));
            dm.setUrl(properties.getProperty("db_url"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dm;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
