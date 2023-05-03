/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.auth;

import static com.jwtConfig.Constants.*;
import com.jwtConfig.JWTCredential;
import com.jwtConfig.TokenProvider;
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

/**
 *
 * @author kunal
 */
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

//    @Inject
//    LoginBean lbean;
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest req, HttpServletResponse res, HttpMessageContext ctx) throws AuthenticationException {
        try {

             for(Cookie c : (cs = req.getCookies())){
                if(c.getName().equals("Token")){
                    ck = c;
                }
            }
            if (req.getRequestURI().contains("Logout")) {
                req.logout();
                KeepRecord.reset();
                Cookie[] cookies = req.getCookies();

                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if (c.getName().equals("Token")) {
                            c.setValue("");
                            c.setMaxAge(0);
                            res.addCookie(c);
                        }
                    }
                }
                res.sendRedirect("/tlt_1.0");
                return ctx.doNothing();
            }
        } catch (Exception e) {
            System.out.println("Error Found : " + e);
        }

        String token = extractToken(ctx);
        try {
            if (token == null && req.getParameter("username") != null) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");

                Credential credentials = new UsernamePasswordCredential(username, new Password(password));
                result = handler.validate(credentials);

                if (result.getStatus() == Status.VALID) {
                    KeepRecord.setErrorStatus("");
                    AuthenticationStatus status = createToken(result, ctx, res);

                    status = ctx.notifyContainerAboutLogin(result);

                    KeepRecord.setPrincipal(result.getCallerPrincipal());
                    KeepRecord.setRoles(result.getCallerGroups());
                    System.out.println(KeepRecord.getRoles());
                    System.out.println(KeepRecord.getPrincipal());
                    KeepRecord.setCredential(credentials);

                    if (result.getCallerGroups().contains("admin")) {
//                        System.out.println("Token Assigned: "+KeepRecord.getToken());
//                        req.getRequestDispatcher("admin/Admin.jsf").forward(req, res);
                        res.sendRedirect("admin/Admin.jsf");
                    }

                    if (result.getCallerGroups().contains("tourist")) {
//                        System.out.println("Token Assigned: "+KeepRecord.getToken());
//                        req.getRequestDispatcher("tourist/dashboard.jsf").forward(req, res);
                        res.sendRedirect("tourist/dashboard.jsf");

                    }
                } else {
                    KeepRecord.setErrorStatus("Wrong Username or Password");
                    res.sendRedirect("Login.jsf");
                    return ctx.doNothing();
                }
            }
           
            if ((req.getRequestURI().equals("/tlt_1.0/") || req.getRequestURI().contains("Login"))&& KeepRecord.getToken() != null && ck.getName().equals("Token")) {

                System.out.println(req.getRequestURI());
                if (KeepRecord.getRoles().contains("admin")) {
                    res.sendRedirect("admin/Admin.jsf");
                }

                if (KeepRecord.getRoles().contains("tourist")) {
                    res.sendRedirect("tourist/dashboard.jsf");
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
                JWTCredential credential = tokenProvider.getCredentials(token);
                return context.notifyContainerAboutLogin(credential.getPrincipal(), credential.getAuthorities());
            }
            return context.responseUnauthorized();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AuthenticationStatus createToken(CredentialValidationResult result, HttpMessageContext context, HttpServletResponse response) {
        String jwt = tokenProvider.createToken(result.getCallerPrincipal().getName(), result.getCallerGroups(), false);
        KeepRecord.setToken(jwt);
        response.addCookie(new Cookie("Token", jwt));
        context.getResponse().addHeader(AUTHORIZATION_HEADER, BEARER + jwt);
        System.out.println("Token value " + jwt);

        return context.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
    }

    public String extractToken(HttpMessageContext context) {
//        String authorizationHeaer = context.getRequest().getHeader(AUTHORIZATION_HEADER);
//        if (authorizationHeaer != null && authorizationHeaer.startsWith(BEARER)) {
//            String token = authorizationHeaer.substring(BEARER.length(), authorizationHeaer.length());
//            KeepRecord.setToken(token);
//            System.out.println("JWTAuthenticationMechanism - Extract Tokens" + token);
//            return token;
//        }

        String token = null;
        Cookie[] cookies = context.getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Token")) {
                    token = cookie.getValue();
                    KeepRecord.setToken(token);
                    return token;
                }
            }
        }
        return null;
    }

}
