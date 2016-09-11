package com.github.kag0.oauth2;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * https://tools.ietf.org/html/rfc6749#section-5.2
 *
 * Created by nfischer on 9/2/2016.
 */
@JsonDeserialize(using = ErrorType.ErrorTypeDeserializer.class)
public interface ErrorType extends Registry.Named {

	Registry<ErrorType> REGISTRY = new Registry<>(ErrorType.class);

	static Registry registry(){

		return REGISTRY;
	}

	enum StdErrorType implements ErrorType {
		/**
		 * The request is missing a required parameter, includes an
		 * unsupported parameter value (other than grant type),
		 * repeats a parameter, includes multiple credentials,
		 * utilizes more than one mechanism for authenticating the
		 * client, or is otherwise malformed.
		 */
		invalid_request,

		/**
		 * Client authentication failed (e.g., unknown client, no
		 client authentication included, or unsupported
		 authentication method).  The authorization server MAY
		 return an HTTP 401 (Unauthorized) status code to indicate
		 which HTTP authentication schemes are supported.  If the
		 client attempted to authenticate via the "Authorization"
		 request header field, the authorization server MUST
		 respond with an HTTP 401 (Unauthorized) status code and
		 include the "WWW-Authenticate" response header field
		 matching the authentication scheme used by the client.
		 */
		invalid_client,

		/**
		 * The provided authorization grant (e.g., authorization
		 code, resource owner credentials) or refresh token is
		 invalid, expired, revoked, does not match the redirection
		 URI used in the authorization request, or was issued to
		 another client.
		 */
		invalid_grant,

		/**
		 * The authorization grant type is not supported by the
		 * authorization server.
		 */
		unsupported_grant_type,

		/**
		 * The authenticated client is not authorized to use this
		 * authorization grant type.
		 */
		unauthorized_client,

		/**
		 * The resource owner or authorization server denied the
		 * request.
		 */
		access_denied,

		/**
		 * The authorization server does not support obtaining an
		 * authorization code using this method.
		 */
		unsupported_response_type,

		/**
		 * The requested scope is invalid, unknown, malformed, or
		 * exceeds the scope granted by the resource owner.
		 */
		invalid_scope,

		/**
		 * The authorization server encountered an unexpected
		 condition that prevented it from fulfilling the request.
		 (This error code is needed because a 500 Internal Server
		 Error HTTP status code cannot be returned to the client
		 via an HTTP redirect.)
		 */
		server_error,

		/**
		 * The authorization server is currently unable to handle
		 * the request due to a temporary overloading or maintenance
		 * of the server.  (This error code is needed because a 503
		 * Service Unavailable HTTP status code cannot be returned
		 * to the client via an HTTP redirect.)
		 */
		temporarily_unavailable
		;

		StdErrorType(){
			REGISTRY.register(this);
		}
	}

	class ErrorTypeDeserializer extends NamedDeserializer<ErrorType>{
		public ErrorTypeDeserializer() {
			super(REGISTRY, ErrorType.class);
		}
	}

}
