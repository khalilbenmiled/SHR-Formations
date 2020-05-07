package com.soprahr.Services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.QuestionRepository;
import com.soprahr.Repository.QuizRepository;
import com.soprahr.Repository.ReponseRepository;
import com.soprahr.models.Question;
import com.soprahr.models.Quiz;
import com.soprahr.models.Reponse;

import net.minidev.json.JSONObject;

@Service
public class QuizServices {

	@Autowired
	public QuizRepository repository;
	@Autowired
	public ReponseRepository repositoryR;
	@Autowired
	public QuestionRepository repositoryQ;
	
	/*********************************** AJOUTER UN QUIZ PAR PARAM ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject ajouterQuiz(String nomQuiz , int nbrQuestions , int idFormation , ArrayList listQuestionsReponses ) {
		JSONObject jo = new JSONObject();
			
		List<Question> listQuestion = new ArrayList<Question>();
		
		for(Object objet : listQuestionsReponses) {
			List<Reponse> listReponses = new ArrayList<Reponse>();
			LinkedHashMap obj = (LinkedHashMap) objet;
			String question = (String) obj.get("question");
			
			LinkedHashMap reponse1 = (LinkedHashMap) obj.get("reponse1");
			LinkedHashMap reponse2 = (LinkedHashMap) obj.get("reponse2");
			LinkedHashMap reponse3 = (LinkedHashMap) obj.get("reponse3");
			LinkedHashMap reponse4 = (LinkedHashMap) obj.get("reponse4");
			
			Reponse newReponse1 = new Reponse((String) reponse1.get("reponse"), (boolean) reponse1.get("correcte"));		
			Reponse newReponse2 = new Reponse((String) reponse2.get("reponse"), (boolean) reponse2.get("correcte"));
			Reponse newReponse3 = new Reponse((String) reponse3.get("reponse"), (boolean) reponse3.get("correcte"));
			Reponse newReponse4 = new Reponse((String) reponse4.get("reponse"), (boolean) reponse4.get("correcte"));
			
			listReponses.add(repositoryR.save(newReponse1));
			listReponses.add(repositoryR.save(newReponse2));
			listReponses.add(repositoryR.save(newReponse3));
			listReponses.add(repositoryR.save(newReponse4));
			
			Question newQuestion = new Question();
			newQuestion.setQuestion(question);
			newQuestion.setListReponses(listReponses);
			listQuestion.add(repositoryQ.save(newQuestion));
				
			
		}
		Quiz newQuiz = new Quiz();
		newQuiz.setNomQuiz(nomQuiz);
		newQuiz.setNbrQuestion(nbrQuestions);
		newQuiz.setIdFormation(idFormation);
		newQuiz.setListQuestions(listQuestion);
		jo.put("Quiz" , repository.save(newQuiz));
		return jo;
	}
	
	/*********************************** AFFICHER TOUS LES QUIZ ***************************************/
	public JSONObject getAllQuiz() {
		JSONObject jo = new JSONObject();
		if(repository.findAll().size() != 0) {
			jo.put("Quiz", repository.findAll());
			return jo;
		}else {
			jo.put("Error", "La liste des quiz est vide !");
			return jo;
		}
	}
	
	
	
	
	
	
	
}
