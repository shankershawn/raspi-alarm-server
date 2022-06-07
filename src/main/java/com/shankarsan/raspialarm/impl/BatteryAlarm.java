package com.shankarsan.raspialarm.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shankarsan.raspialarm.interfaces.AlarmPulse;

@Component
public class BatteryAlarm {
	
	private static Integer ALARM_PIN = 5;
	//@Autowired @Qualifier(value = "Pi4JContext") private Context context;
	@Autowired
	private AlarmPulse alarmPulse;
	
	public void ringBatteryAlarm() {
		alarmPulse.invoke(ALARM_PIN, 100, TimeUnit.MILLISECONDS);
	}

}
