/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import annotations.Acl;
import java.io.IOException;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author alex
 */
@Provider
public class AclFilter implements ContainerRequestFilter  {
    
    @Context
    private ResourceInfo resourceInfo;
    
    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        
        Acl aclAnnotation = resourceInfo.getResourceClass().getAnnotation(Acl.class);
        Acl methodAnnotation = resourceInfo.getResourceMethod().getAnnotation(Acl.class);
        if (methodAnnotation != null) {
            aclAnnotation = methodAnnotation;
        }
        
        if (aclAnnotation != null && !authenticate(crc.getHeaders().getFirst("Autorization"), aclAnnotation.roles())) {
            Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("YOU SHALL NOT PASS!!").build();
            crc.abortWith(unauthorizedStatus);
        }
        
    }
    
    private Boolean authenticate(String authHeader, String[] requiredRoles) {
        Set<String> roles = new HashSet<>(Arrays.asList(requiredRoles));
        Set<String> userRoles = new HashSet<>(Arrays.asList(new String[] {Acl.ROLE_ANY}));
        
        //@todo: add all other user roles to userRoles from the auth header here
        
        roles.retainAll(userRoles); //will only leave intersection of both sets
        return roles.size() > 0;
    }
    
    
}
