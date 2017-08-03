package com.gp.sync;

import java.security.Principal;

public class GPrincipal implements Principal {

	private final String name;
	private final String passcode;

	public GPrincipal(String name, String passcode) {
		this.name = name;
		this.passcode = passcode;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public String getPasscode() {
		return passcode;
	}

	@Override
	public String toString() {
		return "GPrincipal [name=" + name + ", passcode=" + passcode + "]";
	}

}
