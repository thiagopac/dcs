package br.gov.mg.uberlandia.decserver.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.gov.mg.uberlandia.decserver.service.EnvioService;

@Service
public class ScheduledService {

    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;
    private final long HORA = MINUTO * 60;
    private final long DIA = HORA * 24;

    @Autowired
    private EnvioService envioService;

    private static Logger logger = LoggerFactory.getLogger(ScheduledService.class);

    @Scheduled(fixedRate = 30 * MINUTO)
    public void executarEnvios() {
        try {
            envioService.executarEnviosProgramados();
            logger.info("Executando envios programados");
        } catch (Exception e) {
            logger.error("Erro ao executar envios programados", e);
            throw new RuntimeException("Erro ao executar envios programados.", e);
        }
    }

    @Scheduled(cron = "0 0 23 * * ?") // Todos os dias às 23:00
    public void marcarEnviosLidos() {
        try {
            envioService.marcarEnviosComoLidos();
            logger.info("Marcando envios como lidos");
        } catch (Exception e) {
            logger.error("Erro ao marcar envios como lidos", e);
            throw new RuntimeException("Erro ao marcar envios como lidos.", e);
        }
    }
}
