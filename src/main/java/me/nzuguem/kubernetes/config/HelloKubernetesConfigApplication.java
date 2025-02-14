package me.nzuguem.kubernetes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HelloKubernetesConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloKubernetesConfigApplication.class, args);
	}

	@RestController
	class HelloHelloKubernetesConfigController {

		private final HelloKubernetesConfig helloKubernetesConfig;
		private final HelloKubernetesSecret helloKubernetesSecret;

        HelloHelloKubernetesConfigController(HelloKubernetesConfig helloKubernetesConfig, HelloKubernetesSecret helloKubernetesSecret) {
            this.helloKubernetesConfig = helloKubernetesConfig;
            this.helloKubernetesSecret = helloKubernetesSecret;
        }

        @GetMapping
		String hello() {
			return "Hello %s".formatted(this.helloKubernetesConfig.getTarget());
		}

		@GetMapping("/secret")
		String helloSecret() {
			return "Hello %s".formatted(this.helloKubernetesSecret.getPassword());
		}
    }

}
