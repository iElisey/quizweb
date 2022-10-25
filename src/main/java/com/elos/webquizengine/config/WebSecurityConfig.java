package com.elos.webquizengine.config;

import com.elos.webquizengine.model.Role;
import com.elos.webquizengine.model.User;
import com.elos.webquizengine.repo.UserRepository;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/get/**", "api/quizzes/**", "/api/quizzes","/login**", "/js/**", "/error**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/quizzes/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/quizzes/**").authenticated()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepository userRepository) {
        return map -> {
            String id = (String) map.get("sub");
            User user = userRepository.findById(id).orElseGet(()->{
                User newUser = new User();
                newUser.setId(id);
                newUser.setName((String) map.get("name"));
                newUser.setEmail((String) map.get("email"));

                if (newUser.getEmail().equals("yelisei.osadchyi@gmail.com")) {
                    newUser.setRole(Role.ADMIN);
                } else {
                    newUser.setRole(Role.USER);
                }

                newUser.setGender((String) map.get("gender"));
                newUser.setLocale((String) map.get("locale"));
                newUser.setUserpic((String) map.get("picture"));

                return newUser;


            });

            user.setLastVisit(LocalDateTime.now());
            return userRepository.save(user);
        };
    }

}
