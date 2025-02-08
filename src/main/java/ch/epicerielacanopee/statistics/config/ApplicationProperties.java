package ch.epicerielacanopee.statistics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Statistics.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Sendgrid sendgrid = new Sendgrid();
    private final Api api = new Api();
    private final From from = new From();

    public Sendgrid getSendgrid() {
        return sendgrid;
    }

    public Api getApi() {
        return api;
    }

    public From getFrom() {
        return from;
    }

    public static class Sendgrid {

        private Api api;
        private From from;

        public Api getApi() {
            return api;
        }

        public void setApi(Api api) {
            this.api = api;
        }

        public From getFrom() {
            return from;
        }

        public void setFrom(From from) {
            this.from = from;
        }
    }

    public static class Api {

        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static class From {

        private String email;
        private String name;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
