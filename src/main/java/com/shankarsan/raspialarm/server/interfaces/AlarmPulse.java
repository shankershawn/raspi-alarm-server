package com.shankarsan.raspialarm.server.interfaces;

import java.util.concurrent.TimeUnit;

@FunctionalInterface
public interface AlarmPulse {
	void invoke(Integer pulseCount, Integer pulseMillis, Integer intervalMillis, TimeUnit timeunit);
}
