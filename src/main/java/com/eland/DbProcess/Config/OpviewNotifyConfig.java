package com.eland.DbProcess.Config;


import com.zaxxer.hikari.HikariDataSource;
        import org.springframework.beans.factory.annotation.Qualifier;
        import org.springframework.boot.context.properties.ConfigurationProperties;
        import org.springframework.boot.jdbc.DataSourceBuilder;
        import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
        import org.springframework.jdbc.core.JdbcTemplate;
        import org.springframework.orm.jpa.JpaTransactionManager;
        import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
        import org.springframework.transaction.PlatformTransactionManager;
        import org.springframework.transaction.annotation.EnableTransactionManagement;

        import javax.persistence.EntityManagerFactory;
        import javax.sql.DataSource;

@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "opviewNotifyEntityManagerFactory",
        transactionManagerRef = "opviewNotifyTransactionManager",
        basePackages = { "com.eland.DbProcess.opview_notify.Repository" })
@Configuration
public class OpviewNotifyConfig {

    @ConfigurationProperties(prefix = "opview-notify.datasource")
    @Bean(name = "opviewNotifyDataSource")
    public DataSource dataSource () {
        HikariDataSource ds = (HikariDataSource) DataSourceBuilder.create().build(); //spring默認的的資料庫連線池
        ds.setConnectionTestQuery("SELECT 1");
        return ds;
    }

    @Bean(name = "opviewNotifyEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory (
            EntityManagerFactoryBuilder builder,
            @Qualifier("opviewNotifyDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.eland.DbProcess.opview_notify.Entity")
                .persistenceUnit("opview_notify_persistenceUnit").build();
    }

    @Bean(name = "opviewNotifyTransactionManager")
    public PlatformTransactionManager transactionManager (
            @Qualifier("opviewNotifyEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "opviewNotifyJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("opviewNotifyDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

