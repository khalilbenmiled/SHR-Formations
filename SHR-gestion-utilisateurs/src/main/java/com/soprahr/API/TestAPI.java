package com.soprahr.API;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.utils.Utils;

@RestController
@RequestMapping(value = "/test")
public class TestAPI {

	@GetMapping
	public void test() {
		Utils utils = new Utils();
		System.out.println(utils.generatePassword());
	}
}
