package com.tushar.spring.boot.rest.randomNumberGenerator;

public class RandonNumber {
	private final long id;
	private final int random;

	public RandonNumber(long id, int random) {
		this.id = id;
		this.random = random;
	}

	public long getId() {
		return id;
	}

	public int getRandom() {
		return random;
	}

}
