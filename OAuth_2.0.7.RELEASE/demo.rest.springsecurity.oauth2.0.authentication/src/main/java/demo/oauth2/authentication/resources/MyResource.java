package demo.oauth2.authentication.resources;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/MyResource")
public class MyResource {
	
	@GET
	@Path("/createInfo")
	public String createInfo(){
		return "\n\n\t!!!Protected Resource(createInfo) Accessed !!!! Returning from Myresource createInfo\n";
	}
	
	@GET
	@Path("/getMyInfo")
	public String getMyInfo(){
		return "\n\n\t Protected Resource(getMyInfo) Accessed !!!! Returning from Myresource getMyInfo" + new Date() +"\n";
	}
	
	@GET
	@Path("/updateInfo")
	public String updateMyInfo(){
		return "\n\n\t Protected Resource(updateInfo) Accessed !!!! Returning from Myresource updateInfo\n";
	}
}
