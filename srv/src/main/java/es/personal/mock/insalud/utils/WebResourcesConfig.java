package es.personal.mock.insalud.utils;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebResourcesConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Esto le dice a Spring: "Si alguien pide algo, búscalo en la carpeta static del JAR"
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
