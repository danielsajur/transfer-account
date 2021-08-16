package br.com.cvc.banktransfer;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class BankTransferApplication {

	private static final String MESSAGES_SOURCE_FILEMANE = "messages";

	public static void main(String[] args) {
		SpringApplication.run(BankTransferApplication.class, args);
	}
	
//	@Bean
//	// Configuring the internationalization for the current locale 
//	public LocaleResolver localeResolver() {
//		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
//		localeResolver.setDefaultLocale(Locale.US);
//		return localeResolver;
//	}
	
	// Configuring the resource bundle 
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(MESSAGES_SOURCE_FILEMANE);
		return messageSource;
	}

}
