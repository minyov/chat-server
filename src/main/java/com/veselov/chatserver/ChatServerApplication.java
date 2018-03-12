package com.veselov.chatserver;

import com.veselov.chatserver.database.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableTransactionManagement
@Import(DatabaseConfig.class)
public class ChatServerApplication extends SpringBootServletInitializer {

	private final DatabaseConfig databaseConfig;

	@Autowired
	public ChatServerApplication(DatabaseConfig databaseConfig) {
		this.databaseConfig = databaseConfig;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(databaseConfig.dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(databaseConfig.jpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.veselov.*");
		entityManagerFactoryBean.setPersistenceUnitName("chat-server");

		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager jpaTransactionManager(EntityManagerFactory factory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();

		jpaTransactionManager.setEntityManagerFactory(factory);

		return jpaTransactionManager;
	}

	@Bean
	public MultipartConfigElement multipartConfigFactory() {
		MultipartConfigFactory factory = new MultipartConfigFactory();

		factory.setMaxRequestSize("10MB");
		factory.setMaxFileSize("10MB");

		return factory.createMultipartConfig();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ChatServerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ChatServerApplication.class, args);
	}
}
