package school21.spring.service.beans;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;

@Configuration
@PropertySource("db.properties")
@ComponentScan(basePackages = "school21.spring.service")
public class ApplicationConfig {

    @Autowired private Environment env;

    @Bean(value = "HikariDataSource")
    public DataSource hikariDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(env.getProperty("db.url"));
        ds.setUsername(env.getProperty("db.user"));
        ds.setPassword(env.getProperty("db.password"));
        ds.setDriverClassName(env.getProperty("db.driver.name"));
        return ds;
    }

    @Bean(value = "DriverManagerDataSource")
    public DataSource springDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(env.getProperty("db.url"));
        ds.setUsername(env.getProperty("db.user"));
        ds.setPassword(env.getProperty("db.password"));
        ds.setDriverClassName(env.getProperty("db.driver.name"));
        return ds;
    }
}
