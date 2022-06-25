package com.shankarsan.raspialarm.server.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shankarsan.raspialarm.common.dto.MessageDTO;
import com.shankarsan.raspialarm.server.interfaces.AlarmPulse;

@Service
public class BatteryAlarmService {
	
	@Autowired private AlarmPulse alarmPulse;
	
	public void ringBatteryAlarm(MessageDTO messageDTO) {
		alarmPulse.invoke(messageDTO.getPulseCount(), messageDTO.getPulseMillis(), messageDTO.getIntervalMillis(), TimeUnit.MILLISECONDS);
	}

}
