/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jwtConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author kunal
 */

//Identity  Store
@DatabaseIdentityStoreDefinition(
dataSourceLookup = "jdbc/TLT",
        callerQuery = "Select password from `user_master` where username = ?",
        groupsQuery = "select role from `user_role` where username = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        priority = 30
)

@ApplicationScoped  
public class Project {
    
}
