package com.shankarsan.raspialarm.client.deserialization;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shankarsan.raspialarm.common.dto.MessageDTO;

@Component
public class MessageDeserializer implements Deserializer<MessageDTO> {
	
	private final ObjectMapper mapper = new ObjectMapper();

	public MessageDeserializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public MessageDTO deserialize(String topic, byte[] data) {
		if(null == data) {
			return null;
		}
		try {
			return mapper.readValue(data, MessageDTO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
