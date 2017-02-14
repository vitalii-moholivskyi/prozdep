package department.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.sql.DataSource;
import javax.validation.ValidatorFactory;

/**
 * Created by mogo on 2/14/17.
 */
@ComponentScan("department")
@PropertySource("properties/jdbc.properties")
@Configuration
public class MainConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        dataSource.setMinIdle(1);
        dataSource.setMaxIdle(2);
        dataSource.setInitialSize(1);

        return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public ValidatorFactory getLocalValidatorFactoryBean(){
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public InitializingBean getMethodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }

}
