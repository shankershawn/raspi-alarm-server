package com.shankarsan.raspialarm.server.configuration;

import java.util.Calendar;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.shankarsan.raspialarm.server.interfaces.AlarmPulse;

@Configuration
public class AlarmConfig {
	
private static Logger _log = LoggerFactory.getLogger(AlarmConfig.class);
	
	@Value("${battery-alarm-service.ring-battery-alarm.pin}")
	private Integer pin;

	private Context context;
	
	@Bean("Pi4JContext")
	public Context getContext() {
		context = Pi4J.newAutoContext();
		return context;
	}
	
	@PreDestroy
	public void onExit() {
		_log.info("Shutting down Pi4JContext.");
		if(null != context) {
			context.shutdown();
		}
	}
	
	@Bean("AlarmDigitalOutput")
	public DigitalOutput getDigitalOutput(@Qualifier("Pi4JContext") Context context) {
		return DigitalOutput.newBuilder(context)
				.initial(DigitalState.LOW)
				.shutdown(DigitalState.LOW)
				.provider("pigpio-digital-output")
				.address(pin)
				.id(Long.toString(Calendar.getInstance().getTimeInMillis()))
				.build();
	}
	
	@Bean
	public AlarmPulse getAlarmPulse(@Qualifier("AlarmDigitalOutput") DigitalOutput digitalOutput) {
		return (pulseCount, pulseMillis, intervalMillis, timeunit) -> {
			try {
				while(pulseCount-- > 0) {
					digitalOutput.pulse(pulseMillis, timeunit);
					Thread.sleep(intervalMillis);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				
			}
		};
	}
}
