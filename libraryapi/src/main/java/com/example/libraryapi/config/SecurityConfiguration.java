package com.example.libraryapi.config;

import com.example.libraryapi.security.CustomDetailsService;
import com.example.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // Quando habilito este bean sobrepoe o padrão que gera  a chave de autenticacao
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable) // desabilita para outras apps terem acesso
                //.formLogin( configurer -> configurer.loginPage("/login").successForwardUrl("/home.html")) // se quiser carregar pagina de login
                .formLogin( configurer ->
                        configurer.loginPage("/login").permitAll()) // se quiser carregar pagina de login
                //.formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())

                .authorizeHttpRequests(authorize -> {
                        authorize.requestMatchers("/login").permitAll();
                        authorize.requestMatchers("/autores/**").hasRole("ADMIN"); // apenas admin pode acessar
                        //authorize.requestMatchers(HttpMethod.POST,"/autores/**").hasRole("ADMIN"); //se quiser uma rota/ methodo especifico
                        authorize.requestMatchers("/livros/**").hasAnyRole("ADMIN", "USER");

                        authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll(); // todos podem cadastrar

                        authorize.anyRequest().authenticated();
                        // tudo que vier abaixo perde  funcao
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }



    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService){

    // em memoria usuado apenas para mocar usuarios
        /*
        public UserDetailsService userDetailsService(PasswordEncoder encoder){

        UserDetails user1 = User.builder()
                .username("usuario")
                .password(encoder.encode("1234"))
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                    .username("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
         */

        return new CustomDetailsService(usuarioService);
    }


}
