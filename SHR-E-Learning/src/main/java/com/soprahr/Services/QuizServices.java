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
	
	/*********************************** SUPPRIMER QUIZ ***************************************/
	public JSONObject deleteQuiz(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			Quiz quiz = repository.findById(id).get();
			
			repository.delete(quiz);
			jo.put("Success" , "Quiz supprimé");
			return jo;
		}else {
			jo.put("Error", "Quiz n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** AJOUTER QUIZ A UNE FORMATION ***************************************/
	public JSONObject ajouterQuizToFormation(int idQ, int idF) {
		JSONObject jo = new JSONObject();
		if(repository.findById(idQ).isPresent()) {
			Quiz quiz = repository.findById(idQ).get();
			Quiz newQuiz = new Quiz();
			newQuiz.setIdFormation(idF);
			newQuiz.setNomQuiz(quiz.getNomQuiz());
			newQuiz.setNbrQuestion(quiz.getNbrQuestion());
			newQuiz.setListQuestions(quiz.getListQuestions());
			jo.put("Quiz", repository.save(newQuiz));
			return jo;
		}else {
			jo.put("Error", "Quiz n'existe pas !");
			return jo;
		}
	}
	
	
	/*********************************** UPDATE QUIZ ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject updateQuestion(int id , String question , Object reponse1 , Object reponse2 , Object reponse3 , Object reponse4) {
		JSONObject jo = new JSONObject();
		
		LinkedHashMap obj1 = (LinkedHashMap) reponse1;
		int idrep1 = (int) obj1.get("id");
		String reponse_1 = (String) obj1.get("reponse");
		boolean correct_1 = (boolean) obj1.get("correcte");
		
		LinkedHashMap obj2 = (LinkedHashMap) reponse2;
		int idrep2 = (int) obj2.get("id");
		String reponse_2 = (String) obj2.get("reponse");
		boolean correct_2 = (boolean) obj2.get("correcte");
		
		LinkedHashMap obj3 = (LinkedHashMap) reponse3;
		int idrep3 = (int) obj3.get("id");
		String reponse_3 = (String) obj3.get("reponse");
		boolean correct_3 = (boolean) obj3.get("correcte");
		
		LinkedHashMap obj4 = (LinkedHashMap) reponse4;
		int idrep4 = (int) obj4.get("id");
		String reponse_4 = (String) obj4.get("reponse");
		boolean correct_4 = (boolean) obj4.get("correcte");
		
		if(repositoryQ.findById(id).isPresent()) {
			Question questionToEdit = repositoryQ.findById(id).get();
			questionToEdit.setQuestion(question);
			List<Reponse> listReponses = new ArrayList<Reponse>();
			Reponse rep1 = repositoryR.findById(idrep1).get();
			rep1.setReponse(reponse_1);
			rep1.setCorrecte(correct_1);
			
			Reponse rep2 = repositoryR.findById(idrep2).get();
			rep2.setReponse(reponse_2);
			rep2.setCorrecte(correct_2);
			
			Reponse rep3 = repositoryR.findById(idrep3).get();
			rep3.setReponse(reponse_3);
			rep3.setCorrecte(correct_3);
			
			Reponse rep4 = repositoryR.findById(idrep4).get();
			rep4.setReponse(reponse_4);
			rep4.setCorrecte(correct_4);
			
			listReponses.add(repositoryR.save(rep1));
			listReponses.add(repositoryR.save(rep2));
			listReponses.add(repositoryR.save(rep3));
			listReponses.add(repositoryR.save(rep4));
			
			questionToEdit.setListReponses(listReponses);
			jo.put("Question",repositoryQ.save(questionToEdit));
			return jo;
		}else {
			jo.put("Error" , "Question n'existe pas !");
			return jo;
		}
	}
	
	
	
	
	
	
	
	
}
