package com.ddominguezh.hibernate.shared.infrastructure.repository;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ddominguezh.hibernate.core.shared.infrastructure.config.Parameter;
import com.ddominguezh.hibernate.core.shared.infrastructure.config.ParameterNotExist;
import com.ddominguezh.hibernate.shared.infrastructure.hibernate.HibernateConfigurationFactory;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

	private final HibernateConfigurationFactory factory;
    private final Parameter config;
    private final String contextName;

    public HibernateConfiguration(HibernateConfigurationFactory factory) throws ParameterNotExist {
        this.factory = factory;
        this.config  = new Parameter();
        this.contextName = this.config.get("backoffice.package.name");
    }

    @Bean("transaction_manager")
    public PlatformTransactionManager hibernateTransactionManager() throws IOException, ParameterNotExist {
        return factory.hibernateTransactionManager(sessionFactory());
    }

    @Bean("session_factory")
    public LocalSessionFactoryBean sessionFactory() throws IOException, ParameterNotExist {
        return factory.sessionFactory(contextName, dataSource());
    }

    @Bean("data_source")
    public DataSource dataSource() throws IOException, ParameterNotExist {
        return factory.dataSource(
    		this.config.get("spring.datasource.driver"),
    		this.config.get("spring.datasource.url"),
    		this.config.get("spring.datasource.name"),
    		this.config.get("spring.datasource.user"),
    		this.config.get("spring.datasource.password")
        );
    }
    
}
