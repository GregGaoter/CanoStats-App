package ch.epicerielacanopee.statistics.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfiguration {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public SendGrid getSendGrid() {
        return new SendGrid(applicationProperties.getSendgrid().getApi().getKey());
    }
}
