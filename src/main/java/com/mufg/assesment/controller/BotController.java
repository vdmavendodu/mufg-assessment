package com.mufg.assesment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mufg.assesment.payload.Input;
import com.mufg.assesment.payload.Output;
import com.mufg.assesment.service.BotService;

@RestController
public class BotController {
	
	@Autowired
	private BotService service;
	
	@PostMapping("/updatePosition")
	public String updatePosition(@RequestBody Input input) throws Exception {
		return service.updatePosition(input);
	}
	
	@GetMapping("/getCurrentPosition")
	public Output getCurrentPosition() throws Exception {
		return service.getCurrentPosition();
	}
}
