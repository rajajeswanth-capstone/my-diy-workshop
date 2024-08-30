package com.doityourself.workshop;

import com.doityourself.workshop.common.filter.SessionFilter;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collections;

/**
 * Spring Boot Application class
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.doityourself.workshop"})
@EnableJpaRepositories(basePackages="com.doityourself.workshop.database.repositories")
@EnableTransactionManagement
@EntityScan(basePackages="com.doityourself.workshop.database.entities")
@PropertySources(value = {
		@PropertySource(value = "error.properties")
})
public class DiyWorkshopApplication {
	/**
	 * Main method entry point for sprint boot application startup
	 *
	 * @param args {@link String}[]
	 */
	public static void main(String[] args) {
		new SpringApplicationBuilder(DiyWorkshopApplication.class)
				.headless(false)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

	/**
	 * Register session filter by url pattern
	 *
	 * @return {@link FilterRegistrationBean}&lt;{@link SessionFilter}&gt;
	 */
	@Bean
	public FilterRegistrationBean<SessionFilter> sessionFilter() {
		FilterRegistrationBean<SessionFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new SessionFilter());
		bean.setUrlPatterns(Collections.singletonList("/workshop/*"));
		bean.setOrder(1);
		return bean;
	}

	@Bean
	BasicPasswordEncryptor passwordEncryptor() {
		return new BasicPasswordEncryptor();
	}

	/**
	 * Application home bean
	 *
	 * @return {@link ApplicationHome}
	 */
	@Bean
	public ApplicationHome applicationHome() {

		return new ApplicationHome(DiyWorkshopApplication.class);
	}
}
