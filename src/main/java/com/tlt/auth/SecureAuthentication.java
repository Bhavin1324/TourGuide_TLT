package com.tlt.auth;

import static com.tlt.constants.JwtConstants.*;
import com.tlt.JwtConfig.JWTCredential;
import com.tlt.JwtConfig.TokenProvider;
import static com.tlt.constants.UrlConstants.TO_ADMIN;
import static com.tlt.constants.UrlConstants.TO_GUIDE;
import static com.tlt.constants.UrlConstants.TO_LOGIN;
import static com.tlt.constants.UrlConstants.TO_TOURIST;
import com.tlt.record.KeepRecord;
import java.io.Serializable;
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

@Named
@RequestScoped
public class SecureAuthentication implements Serializable, HttpAuthenticationMechanism {

    @Inject
    IdentityStoreHandler handler;
    CredentialValidationResult result;
    AuthenticationStatus status;

    @Inject
    TokenProvider tokenProvider;
    Cookie[] cs;
    Cookie ck;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext ctx) throws AuthenticationException {
        try {

            for (Cookie c : (cs = request.getCookies())) {
                if (c.getName().equals(TOKEN)) {
                    ck = c;
                }
            }
            if (request.getRequestURI().contains("Logout")) {
                request.logout();
                KeepRecord.reset();
                Cookie[] cookies = request.getCookies();

                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if (c.getName().equals(TOKEN)) {
                            c.setValue("");
                            c.setMaxAge(0);
                            response.addCookie(c);
                        }
                    }
                }
                response.sendRedirect(TO_LOGIN);
                return ctx.doNothing();
            }
        } catch (Exception e) {
            System.out.println("Error Found : " + e);
        }

        String token = extractToken(ctx);
        try {
            if (token == null && request.getParameter("username") != null) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                Credential credentials = new UsernamePasswordCredential(username, new Password(password));
                result = handler.validate(credentials);

                if (result.getStatus() == Status.VALID) {
                    KeepRecord.setErrorStatus("");
                    AuthenticationStatus status = createToken(result, ctx, response);

                    status = ctx.notifyContainerAboutLogin(result);

                    KeepRecord.setPrincipal(result.getCallerPrincipal());
                    KeepRecord.setRoles(result.getCallerGroups());
                    KeepRecord.setCredential(credentials);

                    if (result.getCallerGroups().contains("admin")) {
                        response.sendRedirect(TO_ADMIN);
                    }
                    if (result.getCallerGroups().contains("guide")) {
                        response.sendRedirect(TO_GUIDE);
                    }
                    if (result.getCallerGroups().contains("tourist")) {
                        response.sendRedirect(TO_TOURIST);
                    }
                } else {
                    KeepRecord.setErrorStatus("Wrong Username or Password");
                    response.sendRedirect(TO_LOGIN);
                    return ctx.doNothing();
                }
            }

            if ((request.getRequestURI().equals("/TheLandmarkTour/") || request.getRequestURI().contains("Login")) && KeepRecord.getToken() != null && ck.getName().equals(TOKEN)) {

                System.out.println(request.getRequestURI());
                if (KeepRecord.getRoles().contains("admin")) {
                    response.sendRedirect(TO_ADMIN);
                }
                if (KeepRecord.getRoles().contains("guide")) {
                    response.sendRedirect(TO_GUIDE);
                }
                if (KeepRecord.getRoles().contains("tourist")) {
                    response.sendRedirect(TO_TOURIST);
                }
            }

            if (KeepRecord.getToken() != null) {
                ctx.notifyContainerAboutLogin(KeepRecord.getPrincipal(), KeepRecord.getRoles());
            }

            if (token != null) {
                // validation of the jwt credential
                return validateToken(token, ctx);
            } else if (ctx.isProtected()) {
                // A protected resource is a resource for which a constraint has been defined.
                // if there are no credentials and the resource is protected, we response with unauthorized status
                return ctx.responseUnauthorized();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ctx.doNothing();
    }

    public AuthenticationStatus validateToken(String token, HttpMessageContext context) {
        try {
            if (tokenProvider.validateToken(token)) {
                JWTCredential credential = tokenProvider.getCredential(token);
                return context.notifyContainerAboutLogin(credential.getPrincipal(), credential.getAuthorities());
            }
            return context.responseUnauthorized();
        } catch (Exception ex) {
            ex.printStackTrace();
            return context.responseUnauthorized();
        }
    }

    public AuthenticationStatus createToken(CredentialValidationResult result, HttpMessageContext context, HttpServletResponse response) {
        String jwt = tokenProvider.createToken(result.getCallerPrincipal().getName(), result.getCallerGroups(), false);
        KeepRecord.setToken(jwt);
        context.getResponse().addCookie(new Cookie(TOKEN, BEARER + jwt));
        return context.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
    }

    public String extractToken(HttpMessageContext context) {
        Cookie[] cookies = context.getRequest().getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(TOKEN)) {
                    String token = c.getValue().substring(BEARER.length(), c.getValue().length());
                    KeepRecord.setToken(token);
                    return token;
                }
            }
        }
        return null;
    }
}
