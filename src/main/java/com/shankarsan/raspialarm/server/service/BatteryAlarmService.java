package com.shankarsan.raspialarm.server.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.shankarsan.raspialarm.common.dto.MessageDTO;
import com.shankarsan.raspialarm.server.interfaces.AlarmPulse;

@Service
public class BatteryAlarmService {
	
	private AlarmPulse alarmPulse;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public void ringBatteryAlarm(MessageDTO messageDTO) {
		alarmPulse = applicationContext.getBean(AlarmPulse.class);
		alarmPulse.invoke(messageDTO.getPulseCount(), messageDTO.getPulseMillis(), messageDTO.getIntervalMillis(), TimeUnit.MILLISECONDS);
	}

}
