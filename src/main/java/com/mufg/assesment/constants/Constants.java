package com.mufg.assesment.constants;

public enum Constants {	
	SUCCESS_MESSAGE("Success"),
    FAILURE_MESSAGE("Fail"),
	
	FILE_PATH("D:\\Work Space\\mufg\\mufg\\botmoves.xml");
	

	private String value;
	
	Constants(String values){
		this.value = values;
	}
	public String getValue(){
		return value;
	}
	
	private boolean isStatus;
	
	private Constants(boolean isStatus) {
		this.isStatus = isStatus;
	}
	
	public boolean isStatus() {
		return this.isStatus;
		
	}
}
