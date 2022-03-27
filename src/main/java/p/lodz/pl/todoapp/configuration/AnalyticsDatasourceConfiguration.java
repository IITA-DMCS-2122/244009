package p.lodz.pl.todoapp.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = {"p.lodz.pl.todoapp.repositories.analytics"},
    entityManagerFactoryRef = "analyticsEntityManagerFactory",
    transactionManagerRef = "analyticsTransactionManager")
public class AnalyticsDatasourceConfiguration {

    @Bean(name = "analyticsDataSource")
    @ConfigurationProperties(prefix = "datasource.analytics")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "analyticsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(dataSource())
            .packages("p.lodz.pl.todoapp.models.entities")
            .properties(Map.of(
                "hibernate.hbm2ddl.auto", "create"
            ))
            .build();
    }

    @Bean(name = "analyticsTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("analyticsEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
