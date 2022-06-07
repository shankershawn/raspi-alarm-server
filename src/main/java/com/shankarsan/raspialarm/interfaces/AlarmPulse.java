package com.shankarsan.raspialarm.interfaces;

import java.util.concurrent.TimeUnit;

@FunctionalInterface
public interface AlarmPulse {
	void invoke(Integer pin, Integer pulse, TimeUnit timeunit);
}
