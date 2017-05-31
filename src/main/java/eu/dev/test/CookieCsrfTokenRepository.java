package eu.dev.test;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CookieCsrfTokenRepository implements CsrfTokenRepository {

    public static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";
    public static final String CSRF_HEADER_NAME = "X-XSRF-TOKEN";
    public static final String CSRF_PARAMETER_NAME = "_csrf";

    @Override
    public CsrfToken generateToken( HttpServletRequest request ) {
        return new DefaultCsrfToken( CSRF_HEADER_NAME, CSRF_PARAMETER_NAME,
                UUID.randomUUID().toString() );
    }

    @Override
    public void saveToken( CsrfToken token, HttpServletRequest request,
            HttpServletResponse response ) {
        try {
            Cookie csrfCookie;
            if( token == null ) {
                csrfCookie = new Cookie( CSRF_COOKIE_NAME, "" );
                csrfCookie.setMaxAge( 0 );
            }
            else {
                csrfCookie = new Cookie( CSRF_COOKIE_NAME, token.getToken() );
            }

            URL requestUrl = new URL( request.getRequestURL().toString() );
            csrfCookie.setDomain( requestUrl.getHost() );
            csrfCookie.setPath( "/" );
            csrfCookie.setMaxAge( 86400 );

            response.addCookie( csrfCookie );
        }
        catch( MalformedURLException e ) {
            throw new RuntimeException( e );
        }
    }

    @Override
    public CsrfToken loadToken( HttpServletRequest request ) {
        if( request.getCookies() == null ) {
            return null;
        }

        Optional<Cookie> loadedCookie = Arrays.stream( request.getCookies() )
                .filter( ( cookie ) -> CSRF_COOKIE_NAME.equals( cookie.getName() ) ).findAny();

        if( !loadedCookie.isPresent() ) {
            return null;
        }

        return new DefaultCsrfToken( CSRF_HEADER_NAME, CSRF_PARAMETER_NAME,
                loadedCookie.get().getValue() );
    }
}
