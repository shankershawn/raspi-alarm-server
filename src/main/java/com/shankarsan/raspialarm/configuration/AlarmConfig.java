package com.shankarsan.raspialarm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.exception.IOException;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.shankarsan.raspialarm.interfaces.AlarmPulse;

@Configuration
public class AlarmConfig {
	
	@Bean
	public AlarmPulse getAlarmPulse() {
		return (pin, pulseMillis, timeunit) -> {
			try {
				Context pi4jContext = Pi4J.newAutoContext();
				DigitalOutput digitalOutput = DigitalOutput.newBuilder(pi4jContext)
						.initial(DigitalState.LOW)
						.shutdown(DigitalState.LOW)
						.provider("pigpio-digital-output")
						.address(pin)
						.build();
				digitalOutput.pulse(pulseMillis, timeunit);
				pi4jContext.shutdown();
			} catch (ShutdownException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
	}
}
