package eu.dev.test;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import eu.dev.test.models.SortedResult;

@Configuration
@EnableJpaRepositories( entityManagerFactoryRef = "webEntityManager", transactionManagerRef = "webTransactionManager", basePackageClasses = SortedResult.class )
public class DatabaseConfiguration {

    @Autowired( required = false )
    private PersistenceUnitManager persistenceUnitManager;

    @Bean
    @Primary
    @ConfigurationProperties( "ds.web.jpa" )
    public JpaProperties webJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties( prefix = "ds.web.datasource" )
    public DataSource webDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean webEntityManager(
            JpaProperties webJpaProperties ) {
        EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder( webJpaProperties );
        return builder.dataSource( webDataSource() ).packages( SortedResult.class )
                .persistenceUnit( "web_database" ).build();
    }

    @Bean
    @Primary
    public JpaTransactionManager webTransactionManager( EntityManagerFactory webEntityManager ) {
        return new JpaTransactionManager( webEntityManager );
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(
            JpaProperties webJpaProperties ) {
        JpaVendorAdapter jpaVendorAdapter = createJpaVendorAdapter( webJpaProperties );
        return new EntityManagerFactoryBuilder( jpaVendorAdapter, webJpaProperties.getProperties(),
                this.persistenceUnitManager );
    }

    private JpaVendorAdapter createJpaVendorAdapter( JpaProperties jpaProperties ) {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql( jpaProperties.isShowSql() );
        adapter.setDatabase( jpaProperties.getDatabase() );
        adapter.setDatabasePlatform( jpaProperties.getDatabasePlatform() );
        // adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        adapter.setGenerateDdl( true );
        return adapter;
    }

}