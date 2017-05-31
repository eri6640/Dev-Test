package eu.dev.test;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCsrfCookieFilter extends GenericFilterBean {

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
            throws IOException, ServletException {

        if( !( request instanceof HttpServletRequest )
                || !( response instanceof HttpServletResponse ) ) {
            throw new ServletException( "addCsrfCookieFilter only supports HTTP requests" );
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
        CsrfToken csrfToken = cookieCsrfTokenRepository.loadToken( httpRequest );
        if( csrfToken == null ) {
            csrfToken = cookieCsrfTokenRepository.generateToken( httpRequest );
            cookieCsrfTokenRepository.saveToken( csrfToken, httpRequest, httpResponse );
        }
        else {
            cookieCsrfTokenRepository.saveToken( csrfToken, httpRequest, httpResponse );
        }

        chain.doFilter( request, response );
    }
}
