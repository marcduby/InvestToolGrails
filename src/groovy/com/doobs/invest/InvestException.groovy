package com.doobs.invest

class InvestException extends Exception {
	String newMessage 
	
	public InvestException(String message) {
		super(message)
	}
}
