# OAuth2

![oauth2 logo](https://oauth.net/images/oauth-2-sm.png)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/3a0671eb8065458c879a68c3bba63617)](https://www.codacy.com/app/nfischer921/OAuth2?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=kag0/OAuth2&amp;utm_campaign=Badge_Grade)  
[![Build Status](https://travis-ci.org/kag0/OAuth2.svg?branch=master)](https://travis-ci.org/kag0/OAuth2)  
[![](https://jitpack.io/v/kag0/oauth2.svg)](https://jitpack.io/#kag0/oauth2)  

A set of extensible immutable models for communicating with [OAuth 2](https://tools.ietf.org/html/rfc6749).
This library provides the ability to act in the resource server, client, or authorization server roles defined by OAuth 2.

## Example of general usage to act in authorization server role

```java
TokenRequest request = TokenRequest.parseEncoded(requestBody).get();
if(request instanceof PasswordTokenRequest){
	PasswordTokenRequest passwordRequest = (PasswordTokenRequest) request;
	// confirm that passwordRequest.username() passwordRequest.password() are valid credentials
	if(validCredentials){
		TokenResponse response = ImmutableTokenResponse.builder()
			.accessToken("new token")
			.exipresIn(600)
			.scope(passwordRequest.scope())
			.tokenType(Bearer)
			.build();
		//return response
	}else{
		ErrorResponse response = ImmutableErrorResponse.of(invalid_grant);
		//return response
	}
}
```

## Example of general usage to obtain authorization to access google+ user info

```java
AuthorizationRequest authzRequest = ImmutableAuthorizationRequest.builder()
	.redirectUri(myRedirectEndpoint)
	.clientId(myClientId)
	.scope(HashSet.of(
		"https://www.googleapis.com/auth/plus.login"
		,"https://www.googleapis.com/auth/plus.me"
		,"https://www.googleapis.com/auth/userinfo.email"
		,"https://www.googleapis.com/auth/userinfo.profile"
	)).build();

URI redirect = URI.create("https://accounts.google.com/o/oauth2/v2/auth?" + authzRequest.toFormEncoded());
```
Redirect caller to `redirect`

```java
Either<ErrorResponse, AuthorizationResponse> errorOrAuthzResponse = AuthorizationResponse.parseEncoded(request.getQuery());
if(errorOrAuthzResponse.isLeft())
	//handle error
else{
	TokenRequest tokenRequest = ImmutableSecretClientTokenRequest.of(
		ImmutableCodeTokenRequest.builder()
			.clientId(myClientId)
			.code(errorOrAuthzResponse.get().code())
			.redirectUri(myRedirectEndpoint)
			.build()
		,myClientSecret
	);
}
// POST to www.googleapis.com/oauth2/v4/token with tokenRequest.toFormEncoded() in body
TokenResponse tokenResponse = TokenResponse.parse(responseBody).get;
```

Make requests to api with `tokenResponse.tokenType().name() +' '+ tokenResponse.accessToken()` in `Authorization` header
