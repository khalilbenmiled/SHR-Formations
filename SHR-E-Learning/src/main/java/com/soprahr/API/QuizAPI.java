package com.soprahr.API;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.Services.QuizServices;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/quiz")
public class QuizAPI {
	
	@Autowired
	public QuizServices service;

	@PostMapping(value = "/" , produces = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings("rawtypes")
	public JSONObject ajouterFormation(
			@RequestBody JSONObject quiz
			) {
		String nomQuiz = quiz.getAsString("nomQuiz");
		String nbrQuestion = quiz.getAsString("nbrQuestion");
		String idFormation = quiz.getAsString("idFormation");
			
		ArrayList listQuestionReponse = (ArrayList) quiz.get("listQuestionReponse");
		return service.ajouterQuiz(nomQuiz, Integer.parseInt(nbrQuestion), Integer.parseInt(idFormation), listQuestionReponse);
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllBesoins() {
		return service.getAllQuiz();
	}
}
