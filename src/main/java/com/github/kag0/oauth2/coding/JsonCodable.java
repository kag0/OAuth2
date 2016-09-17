package com.github.kag0.oauth2.coding;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import javaslang.collection.Set;

/**
 * Created by nfischer on 9/3/2016.
 */
public interface JsonCodable {
	ObjectMapper MAPPER = new ObjectMapper()
			.registerModule(new Jdk8Module())
			.registerModule(new SimpleModule()
					.addSerializer(new Scopes.ScopeSetSerializer())
					.addDeserializer(Set.class, new Scopes.ScopeSetDeserializer())
			)
			.setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
			;

	default JsonNode toJson(){
		return MAPPER.valueToTree(this);
	}
}
