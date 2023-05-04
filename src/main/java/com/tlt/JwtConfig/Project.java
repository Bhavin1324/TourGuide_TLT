
package com.tlt.JwtConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;


@DatabaseIdentityStoreDefinition(
dataSourceLookup = "jdbc/TLT",
        callerQuery = "select password from `user_master` where username = ?",
        groupsQuery = "select role from `user_role` where username = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        priority = 30
)

@ApplicationScoped  
public class Project {
    
}
