package eu.dev.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

@SpringBootApplication
public class DevTestApplication {

    public static void main( String[] args ) {
        SpringApplication.run( DevTestApplication.class, args );
    }

    @Configuration
    @EnableWebSecurity
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure( HttpSecurity http ) throws Exception {
            http.authorizeRequests().antMatchers( "/**" ).permitAll().anyRequest().authenticated()
                    .and().csrf().csrfTokenRepository( new CookieCsrfTokenRepository() ).and()
                    .addFilterAfter( new AddCsrfCookieFilter(), CsrfFilter.class );
            ;
        }

    }
}
