package com.tushar.spring.boot.rest.randomNumberGenerator;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/generate/randomnumber")
	public RandonNumber greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new RandonNumber(counter.incrementAndGet(), new Random().nextInt());
	}
}
