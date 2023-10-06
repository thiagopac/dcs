package br.gov.mg.uberlandia.decserver.config;

import br.gov.mg.uberlandia.decserver.interceptor.EAutorizaSessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private EAutorizaSessionInterceptor eautorizaSessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(eautorizaSessionInterceptor);
    }

}