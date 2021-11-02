package com.backend.workflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

// CONFIGURAÇÃO PARA PERMISSÕES DE USUÁRIOS E PERFIS
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/**").hasAnyRole("USER", "ADMIN")
                //.antMatchers("/solicitacoes/**").authenticated()
                //.antMatchers("/pagamentos/**").authenticated()
                .anyRequest().denyAll();
    }

}
