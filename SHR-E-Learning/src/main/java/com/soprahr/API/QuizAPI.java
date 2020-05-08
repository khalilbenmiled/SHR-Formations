package com.soprahr.API;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public JSONObject ajouterQuiz(
			@RequestBody JSONObject quiz
			) {
		String nomQuiz = quiz.getAsString("nomQuiz");
		String nbrQuestion = quiz.getAsString("nbrQuestion");
		String idFormation = quiz.getAsString("idFormation");
			
		ArrayList listQuestionReponse = (ArrayList) quiz.get("listQuestionReponse");
		return service.ajouterQuiz(nomQuiz, Integer.parseInt(nbrQuestion), Integer.parseInt(idFormation), listQuestionReponse);
	}
	
	@PutMapping(value = "/" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject updateQuiz(
			@RequestBody JSONObject quiz
			) {
		String id = quiz.getAsString("id");
		String question = quiz.getAsString("question");
		Object reponse1 = quiz.get("reponse1");
		Object reponse2 = quiz.get("reponse2");
		Object reponse3 = quiz.get("reponse3");
		Object reponse4 = quiz.get("reponse4");
	
		return service.updateQuestion(Integer.parseInt(id),question,reponse1,reponse2,reponse3,reponse4);
	}
	
	@DeleteMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteQuiz(@PathVariable(value="id") int id) {
		return service.deleteQuiz(id);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllBesoins() {
		return service.getAllQuiz();
	}
	
	@PostMapping( value = "/addQTF" ,produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject ajouterQuizToFormation(@Param(value = "idQ") int idQ , @Param(value="idF") int idF) {
		return service.ajouterQuizToFormation(idQ,idF);
	}
	
	
}
