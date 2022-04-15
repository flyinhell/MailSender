package com.eland.DbProcess.Config;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "opvGeneralEntityManagerFactory",
        transactionManagerRef = "opvGeneralTransactionManager",
        basePackages = { "com.eland.DbProcess.opv_general.Repository" })
@Configuration
public class OpvGeneralConfig {

    @Primary
    @ConfigurationProperties(prefix = "opv-general.datasource")
    @Bean(name = "opvGeneralDataSource")
    public DataSource dataSource () {
        HikariDataSource ds = (HikariDataSource) DataSourceBuilder.create().build(); //spring默認的的資料庫連線池
        ds.setConnectionTestQuery("SELECT 1");
        return ds;
    }

    @Primary
    @Bean(name = "opvGeneralEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory (
            EntityManagerFactoryBuilder builder,
            @Qualifier("opvGeneralDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
              //  .packages("com.eland.db.opvdfdf_general.entity")
                .packages("com.eland.DbProcess.opv_general.Entity")
                .persistenceUnit("opv_general_persistenceUnit").build();
    }

    @Primary
    @Bean(name = "opvGeneralTransactionManager")
    public PlatformTransactionManager transactionManager (
            @Qualifier("opvGeneralEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Primary
    @Bean(name = "opvGeneralJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("opvGeneralDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
