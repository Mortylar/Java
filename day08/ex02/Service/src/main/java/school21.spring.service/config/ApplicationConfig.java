package school21.spring.service.beans;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;

@Configuration
public class ApplicationConfig {

    @Bean
    public UsersRepository usersRepository() {
        return new UsersRepositoryJdbcImpl(dataSource());
    }

    @Bean
    public HikariDataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/mortylar");
        ds.setUsername("mortylar");
        ds.setDriverClassName("org.postgresql.Driver");
        return ds;
    }
}
