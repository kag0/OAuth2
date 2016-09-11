package com.github.kag0.oauth2;

/**
 * Created by nfischer on 9/2/2016.
 */
public interface Parameters {
	String grant_type        = "grant_type";
	String scope             = "scope";
	String response_type     = "response_type";
	String client_id         = "client_id";
	String redirect_uri      = "redirect_uri";
	String state             = "state";
	String code              = "code";
	String username          = "username";
	String password          = "password";
	String error_uri         = "error_uri";
	String error_description = "error_description";
	String access_token      = "access_token";
	String token_type        = "token_type";
	String expires_in        = "expires_in";
	String refresh_token     = "refresh_token";
	String error             = "error";
	String client_secret     = "client_secret";
	String client_assertion_type = "client_assertion_type";
	String client_assertion_type_jwt = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer";
	String client_assertion  = "client_assertion";
	String assertion         = "assertion";
}
