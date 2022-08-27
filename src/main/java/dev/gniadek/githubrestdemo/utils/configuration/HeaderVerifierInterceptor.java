package dev.gniadek.githubrestdemo.utils.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gniadek.githubrestdemo.dto.exceptions.GeneralExceptionDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HeaderVerifierInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getHeader("Accept").equals("application/xml")){
            GeneralExceptionDTO generalExceptionDTO = new GeneralExceptionDTO(406,
                    "Your Accept header should accept application/json");

            response.getWriter().write(new ObjectMapper().writeValueAsString(generalExceptionDTO));
            response.setStatus(406);
            response.setContentType("application/json");
            response.flushBuffer();
            return false;
        }

        return true;
    }
}
