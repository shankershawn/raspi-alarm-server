package com.shankarsan.raspialarm.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.shankarsan.raspialarm.impl.BatteryAlarm;

@Component
public class KafkaConsumer {
	
	@Autowired
	private BatteryAlarm batteryAlarm;

	
	@KafkaListener(topics = "battery_level", groupId = "battery_level_group")
	public void listenGroupBatteryLevelGroup(String message) {
		System.out.println(message);
		batteryAlarm.ringBatteryAlarm();
	}
}
