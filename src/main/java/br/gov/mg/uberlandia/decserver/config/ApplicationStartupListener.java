package br.gov.mg.uberlandia.decserver.config;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String host = event.getApplicationContext().getEnvironment().getProperty("server.address");
        String port = event.getApplicationContext().getEnvironment().getProperty("server.port");
        String contextPath = event.getApplicationContext().getEnvironment().getProperty("server.servlet.context-path");
        
        System.out.println("DEC-SERVER iniciado em: http://" + host + ":" + port);
        System.out.println("Context-path: " + contextPath);
    }
}
