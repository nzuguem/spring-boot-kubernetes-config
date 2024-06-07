package me.nzuguem.kubernetes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.hello")
public class HelloKubernetesConfig {
    
    private String target = "Nzuguem";
		
    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}