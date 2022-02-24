package com.admin.config;

import com.constant.CrowFundingConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qiu
 * @create 2022-02-23 12:39
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class CrowdfundingSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CrowdfundingUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder())
        ;
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/index.jsp","/login","/static/**","/WEB-INF/**").permitAll()
                .antMatchers("/add").hasAuthority("user:add")
                .antMatchers("/delete/**").hasAuthority("user:delete")
                .antMatchers("/update").hasAuthority("user:update")
                .antMatchers("/role/save.json").hasAuthority("role:add")
                .antMatchers("/role/delete.json").hasAuthority("role:delete")
                .antMatchers("/role/update.json").hasAuthority("role:update")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().loginPage("/index.jsp").loginProcessingUrl("/security/do/login")
                .usernameParameter("adminAcc").passwordParameter("adminPwd")
                .defaultSuccessUrl("/main")
                .and()
                .logout().logoutUrl("/security/do/logout").logoutSuccessUrl("/index.jsp")
                .and()
                .exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        request.setAttribute("exception",new Exception(CrowFundingConstant.MESSAGE_ACCESS_DENIED));
                        request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,response);
                    }
                })
        ;
    }
}
