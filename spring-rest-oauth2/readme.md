spring-rest-oauth2 - Securing Restful Web Services with Spring Security and OAuth2 (Spring Security + OAuth2 + Spring Rest)
--------------------
OAuth (Open Authentication) is an open standard or kind of protocol that lets one site to share its content with some other site without sharing credentials.

In this post we will discuss how to secure Restful Web Services using Spring security and OAuth2, we will use Spring Security to validate a user on server and OAuth to manage authentication tokens to be used in communication. After applying this implementation only authenticated users and applications will get a valid access token from OAuth and using that token the user can access authorized API’s on server.


The flow of application will go something like this:

1) User sends a GET request to server with five parameters: grant_type, username, password, client_id, client_secret; something like this: 
http://localhost:8080/spring-rest-oauth2/oauth/token?grant_type=password&client_id=restapp&client_secret=restapp&username=test&password=test


2) Server validates the user with help of spring security, and if the user is authenticated, OAuth generates a access token and send sends back to user in following format.

{
"access_token": "22cb0d50-5bb9-463d-8c4a-8ddd680f553f",
"token_type": "bearer",
"refresh_token": "7ac7940a-d29d-4a4c-9a47-25a2167c8c49",
"expires_in": 119
}

Here we got access_token for further communication with server or to get some protected resourses(API’s), it mentioned a expires_in time that indicates the validation time of the token and a refresh_token that is being used to get a new token when token is expired.


3) We access protected resources by passing this access token as a parameter, the request goes something like this:
http://localhost:8080/spring-rest-oauth2/api/users/?access_token=8c191a0f-ebe8-42cb-bc18-8e80f2c4238e 

Here http://localhost:8080/spring-rest-oauth2 is the server path, and /api/users/ Is an API URL that returns a list of users and is being protected to be accessed.


4) If the token is not expired and is a valid token, the requested resources will be returned.

5) In case the token is expired, user needs to get a new token using its refreshing token that was accepted in step(2). A new access token request after expiration looks something like this:
http://localhost:8080/spring-rest-oauth2/oauth/token?grant_type=refresh_token&client_id=restapp&client_secret=restapp&refresh_token=7ac7940a-d29d-4a4c-9a47-25a2167c8c49 
And you will get a new access token along with a new refresh token. 


Integrating Spring Security with OAuth2 to secure Restful Web Services.

In this section we will see how the application works and how to integrate Spring Security with OAuth2, so for now we have a simple Spring Rest structure that returns a list of users for authenticated user, we have a simple spring controller that’s taking the responsibility: 


spring-security.xml
---------------------
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:oauth="http://www.springframework.org/schema/security/oauth2"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:sec="http://www.springframework.org/schema/security" 
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/security/oauth2 
	http://www.springframework.org/schema/security/spring-security-oauth2.xsd
	http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
	http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd 
	http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd ">


	<!-- This is default url to get a token from OAuth -->
	<http pattern="/oauth/token" 
		create-session="stateless"
		authentication-manager-ref="clientAuthenticationManager"
		xmlns="http://www.springframework.org/schema/security">
		
		<intercept-url pattern="/oauth/token" access="IS_AUTHENTICATED_FULLY" />
		<anonymous enabled="false" />
		
		<http-basic entry-point-ref="clientAuthenticationEntryPoint" />
		
		
		<!-- include this only if you need to authenticate clients via request parameters -->
		<custom-filter ref="clientCredentialsTokenEndpointFilter" after="BASIC_AUTH_FILTER" />
		<access-denied-handler ref="oauthAccessDeniedHandler" />
	</http>

	<!-- This is where we tells spring security what URL should be protected 
		and what roles have access to them -->
	<http pattern="/api/**" 
	    create-session="never"
		entry-point-ref="oauthAuthenticationEntryPoint"
		access-decision-manager-ref="accessDecisionManager"
		xmlns="http://www.springframework.org/schema/security">
		
		<anonymous enabled="false" />
		<intercept-url pattern="/api/**" access="ROLE_APP" />
		<custom-filter ref="resourceServerFilter" before="PRE_AUTH_FILTER" />
		<access-denied-handler ref="oauthAccessDeniedHandler" />
	</http>


	<bean id="oauthAuthenticationEntryPoint"
		class="org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint">
		<property name="realmName" value="test" />
	</bean>

	<bean id="clientAuthenticationEntryPoint"
		class="org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint">
		<property name="realmName" value="test/client" />
		<property name="typeName" value="Basic" />
	</bean>

	<bean id="oauthAccessDeniedHandler"
		class="org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler" />

	<bean id="clientCredentialsTokenEndpointFilter"
		class="org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter">
		<property name="authenticationManager" ref="clientAuthenticationManager" />
	</bean>

	<bean id="accessDecisionManager" class="org.springframework.security.access.vote.UnanimousBased"
		xmlns="http://www.springframework.org/schema/beans">
		<constructor-arg>
			<list>
				<bean class="org.springframework.security.oauth2.provider.vote.ScopeVoter" />
				<bean class="org.springframework.security.access.vote.RoleVoter" />
				<bean class="org.springframework.security.access.vote.AuthenticatedVoter" />
			</list>
		</constructor-arg>
	</bean>

	<authentication-manager id="clientAuthenticationManager"
		xmlns="http://www.springframework.org/schema/security">
		<authentication-provider user-service-ref="clientDetailsUserService" />
	</authentication-manager>


	<!-- This is simple authentication manager, with a hardcoded user/password 
		combination. We can replace this with a user defined service to get few users 
		credentials from DB -->
	<authentication-manager alias="authenticationManager"
		xmlns="http://www.springframework.org/schema/security">
		<authentication-provider>
			<user-service>
				<user name="test" password="test" authorities="ROLE_APP" />
			</user-service>
		</authentication-provider>
	</authentication-manager>

	<bean id="clientDetailsUserService"
		class="org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService">
		<constructor-arg ref="clientDetails" />
	</bean>


	<!-- This defined token store, we have used inmemory tokenstore for now 
		but this can be changed to a user defined one -->
	<bean id="tokenStore"
		class="org.springframework.security.oauth2.provider.token.InMemoryTokenStore" />

	<!-- This is where we defined token based configurations, token validity 
		and other things -->
	<bean id="tokenServices"
		class="org.springframework.security.oauth2.provider.token.DefaultTokenServices">
		<property name="tokenStore" ref="tokenStore" />
		<property name="supportRefreshToken" value="true" />
		<property name="accessTokenValiditySeconds" value="84600" />
		<property name="clientDetailsService" ref="clientDetails" />
	</bean>

	<bean id="userApprovalHandler"
		class="org.springframework.security.oauth2.provider.approval.TokenServicesUserApprovalHandler">
		<property name="tokenServices" ref="tokenServices" />
	</bean>

	<oauth:authorization-server
		client-details-service-ref="clientDetails" 
		token-services-ref="tokenServices"
		user-approval-handler-ref="userApprovalHandler">
		<oauth:authorization-code />
		<oauth:implicit />
		<oauth:refresh-token />
		<oauth:client-credentials />
		<oauth:password />
	</oauth:authorization-server>

	<oauth:resource-server id="resourceServerFilter"
		resource-id="test" 
		token-services-ref="tokenServices" />

	<oauth:client-details-service id="clientDetails">
		<!-- client -->
		<oauth:client client-id="restapp"
			authorized-grant-types="authorization_code,client_credentials"
			authorities="ROLE_APP" 
			scope="read,write,trust" 
			secret="secret" />

		<oauth:client client-id="restapp"
			authorized-grant-types="password,authorization_code,refresh_token,implicit"
			secret="restapp" 
			authorities="ROLE_APP" />
	</oauth:client-details-service>
	

	<sec:global-method-security pre-post-annotations="enabled" proxy-target-class="true">
		<sec:expression-handler ref="oauthExpressionHandler" />
	</sec:global-method-security>

	<oauth:expression-handler id="oauthExpressionHandler" />
	
	<oauth:web-expression-handler id="oauthWebExpressionHandler" />
</beans>



