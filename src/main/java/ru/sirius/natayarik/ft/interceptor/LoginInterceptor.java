package ru.sirius.natayarik.ft.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author Egor Malko
 */
public class LoginInterceptor implements HandlerInterceptor {
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      Enumeration<String> headers = request.getHeaderNames();
      while (headers.hasMoreElements()) {
         String headerName = headers.nextElement();
         System.out.println(headers.nextElement() + " " + request.getHeader(headerName));
      }
      return true;
   }
}
