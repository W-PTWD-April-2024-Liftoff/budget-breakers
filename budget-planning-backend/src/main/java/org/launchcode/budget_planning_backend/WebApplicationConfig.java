package org.launchcode.budget_planning_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Replace with your frontend URL
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")  // Frontend URL (React app)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)  // Allow cookies (credentials)
                .allowedHeaders("*");    // Allow all headers
    }
   // @Bean
//    public AuthenticationFilter authenticationFilter() {
//        return new AuthenticationFilter();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor( authenticationFilter() );
//    }

}
