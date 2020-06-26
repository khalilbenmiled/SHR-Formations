package com.soprahr.API;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.utils.Utils;

@RestController
@RequestMapping(value = "/test")
public class TestAPI {

	@GetMapping
	public void test() {
		Map<String, String> env = System.getenv();
		System.out.println(env.get("PROXY"));
	}
}
