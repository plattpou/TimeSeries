/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;


/**
 *
 * @author alex
 */
@Path("/")
public class WelcomeResource {
    
    @Context
    ServletContext servletContext;
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String home() {
        String content = "";
        try {
            content = new String ( Files.readAllBytes( Paths.get(servletContext.getRealPath("/index.html")) ) );
        } catch (IOException ex) {
            Logger.getLogger(WelcomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content;
    }
}
