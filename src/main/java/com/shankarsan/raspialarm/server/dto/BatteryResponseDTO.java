package com.shankarsan.raspialarm.server.dto;

public class BatteryResponseDTO {
	
	private String status;
	private int level;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
