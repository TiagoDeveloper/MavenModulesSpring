package br.com.service;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages="br.com.service.services")
public class PersistenceJPAConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource jndiDataSourceLookup() {
		JndiDataSourceLookup jdsl = new JndiDataSourceLookup();
		return jdsl.getDataSource(this.env.getProperty("jndi.data.source.lookup"));
	}
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
			em.setDataSource(jndiDataSourceLookup());
			em.setPackagesToScan(new String[] { "br.com.service.models" });
			em.setJpaVendorAdapter(this.vendorAdapter());
			em.setJpaProperties(additionalProperties());
		return em;
	}
	@Bean
	public JpaVendorAdapter vendorAdapter() {
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		return vendorAdapter;
	}
	
	public Properties additionalProperties() {
		Properties properties = new Properties();
			properties.setProperty("hibernate.show_sql", this.env.getProperty("hibernate.show_sql"));
			properties.setProperty("hibernate.format_sql", this.env.getProperty("hibernate.format_sql"));
			properties.setProperty("hibernate.hbm2ddl.auto", this.env.getProperty("hibernate.hbm2ddl.auto"));
			properties.setProperty("hibernate.dialect", this.env.getProperty("hibernate.dialect"));
		return properties;
	}
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}
	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

}
