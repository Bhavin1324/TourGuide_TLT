
package com.tlt.JwtConfig;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;


@DatabaseIdentityStoreDefinition(
dataSourceLookup = "jdbc/TLT",
        callerQuery = "select password from `user_master` where username = ?",
        groupsQuery = "select role from `user_role` where username = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        priority = 30
)

@SessionScoped  
public class Project implements Serializable{
    
}
