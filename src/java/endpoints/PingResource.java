package endpoints;

import annotations.Acl;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author alex
 */
@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
@Acl(roles={Acl.ROLE_SUPER_ADMIN, Acl.ROLE_ADMIN})
public class PingResource {

    @GET
    @Acl(roles={Acl.ROLE_ANY}) 
    public String handleGet() {
        return "{\"response\":\"pong\"}";
    }
}
