package git.client.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import git.client.hooks.PushHookV2;
import git.client.utility.ResponseDetail;

@Path("/webhookservice")
public class GitWebHookService {

	private PushHookV2 pushHook;

	@POST
	@Path("/event")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response testMethod(String data, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest){
		Response.ResponseBuilder rBuilder=null;
		Response response =null;
		ResponseDetail responseDetail = new ResponseDetail();
		responseDetail.setId("2");
		responseDetail.setStatus("Sucess");
		ObjectMapper objectMapper = new ObjectMapper();
		String systemPath = "D:\\Praveen\\GitLabHooks\\";
		pushHook = null;
		try {
			pushHook = objectMapper.readValue(data, git.client.hooks.PushHookV2.class);
			objectMapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
			objectMapper.writeValue(new File(systemPath+"receivedRequest"+System.currentTimeMillis()+".json"), pushHook);
		
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		rBuilder = Response.ok(responseDetail).type(MediaType.APPLICATION_JSON);
		response = rBuilder.build();
		return response;
		
	}
}