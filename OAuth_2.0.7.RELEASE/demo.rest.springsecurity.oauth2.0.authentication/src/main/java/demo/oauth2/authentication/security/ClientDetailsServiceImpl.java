package demo.oauth2.authentication.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientDetailsServiceImpl.class);
	
	private static final String CLIENT_CREDENTIALS = "client_credentials";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String PASSWORD = "password";

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws OAuth2Exception {
		LOGGER.debug("~~~~ Executing loadClientByClientId() of ClientDetailsServiceImpl ~~~~");
		
		List<String> authorizedGrantTypes = new ArrayList<>();
		authorizedGrantTypes.add(PASSWORD);
		authorizedGrantTypes.add(REFRESH_TOKEN);
		authorizedGrantTypes.add(CLIENT_CREDENTIALS);

		BaseClientDetails clientDetails = new BaseClientDetails();
		if (clientId.equals("client1")) {
			clientDetails.setClientId("client1");
			clientDetails.setClientSecret("client1");
			clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
			return clientDetails;
		} 
		else if(clientId.equals("client2")){
			clientDetails.setClientId("client2");
			clientDetails.setClientSecret("client2");
			clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
			return clientDetails;
		}
		else{
			throw new NoSuchClientException("No client with requested id: "	+ clientId);
		}
	}
}
