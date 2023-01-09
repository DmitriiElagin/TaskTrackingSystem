package elagin.dmitrii.front.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import elagin.dmitrii.front.views.LoginView;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/images/**")
                .permitAll();

        super.configure(http);

        setLoginView(http, LoginView.class);

    }

}
