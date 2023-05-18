package com.tlt.auth;

import com.tlt.JwtConfig.JWTCredential;
import com.tlt.JwtConfig.TokenProvider;
import static com.tlt.constants.JwtConstants.BEARER;
import static com.tlt.constants.JwtConstants.ROLE_ADMIN;
import static com.tlt.constants.JwtConstants.ROLE_GUIDE;
import static com.tlt.constants.JwtConstants.ROLE_TOURIST;
import static com.tlt.constants.JwtConstants.TOKEN;
import static com.tlt.constants.UrlConstants.TO_ADMIN;
import static com.tlt.constants.UrlConstants.TO_GUIDE;
import static com.tlt.constants.UrlConstants.TO_LOGIN;
import static com.tlt.constants.UrlConstants.TO_TOURIST;
import com.tlt.record.KeepRecord;
import io.jsonwebtoken.ExpiredJwtException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.CredentialValidationResult.Status;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class SecureAuthentication implements HttpAuthenticationMechanism {

    @Inject
    IdentityStoreHandler handler;
    CredentialValidationResult result;

    @Inject
    TokenProvider tokenProvider;
    String token;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {
        try {
            if (request.getRequestURI().contains("Logout")) {
                request.logout();
                KeepRecord.reset();
//                HttpSession session = request.getSession();
                System.out.println("Inside Logout func : ---->" + request.getSession().getAttribute("username"));
                request.getSession().removeAttribute(TOKEN);
                request.getSession().removeAttribute("username");
                clearTokenFromCookie(request, response);
                response.sendRedirect(TO_LOGIN);
                return context.doNothing();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (KeepRecord.getToken() != null) {
            token = KeepRecord.getToken();
        }

        try {
            if (token == null && request.getParameter("username") != null) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                Credential credential = new UsernamePasswordCredential(username, new Password(password));
                result = handler.validate(credential);

                if (result.getStatus() == Status.VALID) {
                    KeepRecord.setErrorStatus(null);
                    AuthenticationStatus status = createToken(result, context);

                    status = context.notifyContainerAboutLogin(result);

                    KeepRecord.setPrincipal(result.getCallerPrincipal());
                    KeepRecord.setRoles(result.getCallerGroups());
                    KeepRecord.setCredential(credential);
                    KeepRecord.setUsername(username);
                    HttpSession session = request.getSession();
//                    if (!session.getAttribute("username").) {
                    session.setAttribute("username", username);
                    System.out.println(session.getAttribute("username"));
//                    }

                    if (result.getCallerGroups().contains(ROLE_ADMIN)) {
                        response.sendRedirect(TO_ADMIN);
                    }
                    if (result.getCallerGroups().contains(ROLE_TOURIST)) {
                        response.sendRedirect(TO_TOURIST);
                    }
                    if (result.getCallerGroups().contains(ROLE_GUIDE)) {
                        response.sendRedirect(TO_GUIDE);
                    }
                    return status;
                } else {
                    KeepRecord.setErrorStatus("Either username or password is wrong!");
                    response.sendRedirect(TO_LOGIN);
                    return context.doNothing();
                }
            }
            if (request.getSession().getAttribute(TOKEN) != null && ((request.getRequestURI().contains("Login")) || request.getRequestURI().equals("/TheLandmarkTour/"))) {
                if (KeepRecord.getRoles().contains(ROLE_ADMIN)) {
                    response.sendRedirect(TO_ADMIN);
                }
                if (KeepRecord.getRoles().contains(ROLE_TOURIST)) {
                    response.sendRedirect(TO_TOURIST);
                }
                if (KeepRecord.getRoles().contains(ROLE_GUIDE)) {
                    response.sendRedirect(TO_GUIDE);
                }
            }

        } catch (Exception ex) {
            System.out.println("Exception occured  " + ex.getMessage());
            ex.printStackTrace();
        }

        if (KeepRecord.getToken() != null) {
            context.notifyContainerAboutLogin(KeepRecord.getPrincipal(), KeepRecord.getRoles());
        }

        if (token != null) {
            return validateToken(token, context);
        } else if (context.isProtected()) {
            return context.responseUnauthorized();
        }
        return context.doNothing();
    }

    private AuthenticationStatus validateToken(String token, HttpMessageContext context) {
        try {
            if (tokenProvider.validateToken(token)) {
                JWTCredential credential = tokenProvider.getCredential(token);
                return context.notifyContainerAboutLogin(credential.getPrincipal(), credential.getAuthorities());
            }
            return context.responseUnauthorized();
        } catch (ExpiredJwtException ex) {
            System.out.println("Token expired exception occured!");
            ex.printStackTrace();
            return context.responseUnauthorized();
        }
    }

    private AuthenticationStatus createToken(CredentialValidationResult result, HttpMessageContext context) {
        String jwt = tokenProvider.createToken(result.getCallerPrincipal().getName(), result.getCallerGroups(), false);
        KeepRecord.setToken(jwt);
        Cookie cookie = new Cookie(TOKEN, BEARER + jwt);
        cookie.setPath("/TheLandmarkTour");
        context.getResponse().addCookie(cookie);
        context.getRequest().getSession().setAttribute(TOKEN, BEARER + jwt);
        return context.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
    }

    private String extractToken(HttpMessageContext context) {
        return context.getRequest().getSession().getAttribute(TOKEN).toString();
    }

    public void clearTokenFromCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(TOKEN)) {
                    c.setValue("");
                    c.setMaxAge(0);
                    c.setPath("/TheLandmarkTour");
                    response.addCookie(c);
                    return;
                }
            }
        }
        return;
    }

    public boolean isCookieExist(HttpMessageContext context) {
        Cookie[] cookies = context.getRequest().getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(TOKEN)) {
                    return true;
                }
            }
        }
        return false;
    }
}
