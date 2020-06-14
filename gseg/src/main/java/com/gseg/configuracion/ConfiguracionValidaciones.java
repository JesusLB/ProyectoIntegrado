package com.gseg.configuracion;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuración de validaciones.
 * @author Jesús López Barragán
 *
 */
@Configuration
public class ConfiguracionValidaciones {
	
	@Bean
	public MessageSource messageResource() {
		final ReloadableResourceBundleMessageSource messageSource =
				new ReloadableResourceBundleMessageSource();
		
		messageSource.setBasename("classpath:errors");
		messageSource.setDefaultEncoding("UTF-8");
		
		
		return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		final LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageResource());
		return bean;
	}

}
