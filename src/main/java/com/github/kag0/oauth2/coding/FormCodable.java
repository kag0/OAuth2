package com.github.kag0.oauth2.coding;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;


/**
 * Created by nfischer on 9/2/2016.
 */
public interface FormCodable {

	Map<?, ?> toForm();

	default String toFormEncoded(){
		Stream<BasicNameValuePair> fields = toForm().entrySet().stream()
				.map(e -> new BasicNameValuePair(
						String.valueOf(e.getKey()),
						String.valueOf(e.getValue())));

		return URLEncodedUtils.format((Iterable<BasicNameValuePair>) fields::iterator, StandardCharsets.UTF_8);
	}

	static Map<String, String> encodedToForm(String encoded){
		return URLEncodedUtils.parse(encoded, StandardCharsets.UTF_8)
				.stream()
				.collect(toMap(
						NameValuePair::getName,
						NameValuePair::getValue
				));
	}

}
