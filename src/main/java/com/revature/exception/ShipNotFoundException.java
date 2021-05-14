package com.revature.exception;

public class ShipNotFoundException extends Exception {

	public ShipNotFoundException() {
	}

	public ShipNotFoundException(String arg0) {
		super(arg0);
	}

	public ShipNotFoundException(Throwable arg0) {
		super(arg0);
	}

	public ShipNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ShipNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
