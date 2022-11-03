package com.shankarsan.raspialarm.server.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.shankarsan.raspialarm.common.dto.MessageDTO;
import com.shankarsan.raspialarm.server.service.BatteryAlarmService;

@Component
public class KafkaConsumer {
	
	@Autowired
	private BatteryAlarmService batteryAlarmService;
	
	@KafkaListener(topics = "battery_level", groupId = "battery_level_group")
	public void listenGroupBatteryLevelGroup(MessageDTO messageDTO) {
		batteryAlarmService.ringBatteryAlarm(messageDTO);
	}
}
