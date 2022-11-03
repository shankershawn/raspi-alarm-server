package com.shankarsan.raspialarm.server.configuration;

import java.util.Calendar;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.shankarsan.raspialarm.server.interfaces.AlarmPulse;

@Configuration
public class AlarmConfig {
	
private static Logger _log = LoggerFactory.getLogger(AlarmConfig.class);
	
	@Value("${raspi.alarm.server.battery-alarm.pin}")
	private Integer pin;

	private Context context;
	
	@Bean("Pi4JContext")
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Context getContext() {
		if(context == null || context.isShutdown()) {
			context = Pi4J.newAutoContext();
		}
		return context;
	}
	
	@PreDestroy
	public void clearPiContext() {
		_log.trace("Shutting down Pi4JContext.");
		if(null != context && !context.isShutdown()) {
			context.shutdown();
			_log.debug("Pi4JContext shutdown complete.");
		}
	}
	
	@Bean("AlarmDigitalOutput")
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public AlarmPulse getAlarmPulse(@Qualifier("AlarmDigitalOutput") DigitalOutput digitalOutput) {
		return (pulseCount, pulseMillis, intervalMillis, timeunit) -> {
			try {
				while(pulseCount-- > 0) {
					digitalOutput.pulse(pulseMillis, timeunit);
					Thread.sleep(intervalMillis);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				clearPiContext();
			}
		};
	}
}
