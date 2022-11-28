package ru.sirius.natayarik.ft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.sirius.natayarik.ft.interceptor.LoginInterceptor;

/**
 * @author Egor Malko
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(new LoginInterceptor());
   }
}
