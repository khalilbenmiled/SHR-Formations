package com.soprahr.Services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.soprahr.Repository.QuestionRepository;
import com.soprahr.Repository.QuizRepository;
import com.soprahr.Repository.ReponseRepository;
import com.soprahr.Repository.ScoreRepository;
import com.soprahr.models.Question;
import com.soprahr.models.Quiz;
import com.soprahr.models.Reponse;
import com.soprahr.models.Score;

import net.minidev.json.JSONObject;

@Service
public class QuizServices {

	@Autowired
	public QuizRepository repository;
	@Autowired
	public ReponseRepository repositoryR;
	@Autowired
	public QuestionRepository repositoryQ;
	@Autowired
	public ScoreRepository repositoryS;
	
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
			
			List<Question> newListQuestions = new ArrayList<Question>();
			for(Question question : quiz.getListQuestions()) {
				Question newQuestion = new Question();		
				newQuestion.setQuestion(question.getQuestion());
				
				List<Reponse> newListReponse = new ArrayList<Reponse>();
				for(Reponse reponse : question.getListReponses()) {
					Reponse newReponse = new Reponse();
					newReponse.setReponse(reponse.getReponse());
					newReponse.setCorrecte(reponse.isCorrecte());
					
					newListReponse.add(repositoryR.save(newReponse));
				}
				
				newQuestion.setListReponses(newListReponse);
				newListQuestions.add(repositoryQ.save(newQuestion));
			}
			
			newQuiz.setListQuestions(newListQuestions);
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
	
	
	/*********************************** LIST QUIZ PAR PARTICIPANT ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getListQuizCollaborateur (int idCollaborateur) {
		JSONObject jo = new JSONObject();
		List<Quiz> listQuizCollaborateur = new ArrayList<Quiz>();
		ResponseEntity<JSONObject> formationsResponse = getFormationsByCollaborateur("http://localhost:8585/formations/byCollaborateur" , idCollaborateur);
		ArrayList formations = (ArrayList) formationsResponse.getBody().get("Formations");
		List<Quiz> listQuiz = repository.findAll();
		List<Score> listScores = repositoryS.findAll();
		for(Object object : formations) {
			LinkedHashMap formation = (LinkedHashMap) object;
			Optional<Quiz> quizCollaborateur = listQuiz.stream().filter(q->q.getIdFormation() == (int) formation.get("id")).findFirst();
			Optional<Score> scoreCollaborateur = listScores.stream().filter(s->s.getQuiz().getId() == quizCollaborateur.get().getId() && s.getIdCollaborateur() == idCollaborateur).findFirst();
			if(quizCollaborateur.isPresent() && !scoreCollaborateur.isPresent()) {
				listQuizCollaborateur.add(quizCollaborateur.get());
			}
		}
		jo.put("Quiz", listQuizCollaborateur);
		return jo;
	}
	
	/*********************************** LIST SCORE ALL COLLABORATEUR  ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getListScore() {
		JSONObject jo = new JSONObject();
		List<JSONObject> listScore = new ArrayList<JSONObject>();
		if(repositoryS.findAll().size() != 0 ) {
			for(Score score : repositoryS.findAll() ) {
				ResponseEntity<JSONObject> user = getUserAPI("http://localhost:8181/users/byId" , score.getIdCollaborateur());
				if(user.getBody().containsKey("Error")) {
					jo.put("Error" , user.getBody().get("Error"));
					return jo;
				}else {
					LinkedHashMap userBody = (LinkedHashMap) user.getBody().get("User");
					JSONObject joo = new JSONObject();
					joo.put("Score" , score);
					joo.put("User" , userBody);
					listScore.add(joo);
				}
			}
			jo.put("Scores", listScore);
			return jo;
		}else {
			jo.put("Error", "La list est vide !");
			return jo;
		}
	}
	
	/*********************************** LIST SCORE BY COLLABORATEUR  ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject getListScoreByCollaborateur(int idCollaborateur) {
		JSONObject jo = new JSONObject();
		List<JSONObject> newListScore = new ArrayList<JSONObject>();
		if(repositoryS.getScoreByCollaborateur(idCollaborateur).size() != 0) {
			List<Score> listScore = repositoryS.getScoreByCollaborateur(idCollaborateur);
			
			ResponseEntity<JSONObject> user = getUserAPI("http://localhost:8181/users/byId" , idCollaborateur);
			if(user.getBody().containsKey("Error")) {
				jo.put("Error" , user.getBody().get("Error"));
				return jo;
			}else {
				for(Score score : listScore) {
					JSONObject joo = new JSONObject();
					LinkedHashMap userBody = (LinkedHashMap) user.getBody().get("User");
					joo.put("User", userBody);
					joo.put("Score" , score);
					newListScore.add(joo);
				}
				
			}
			
			jo.put("Scores" , newListScore);
			return jo;
		}else {
			jo.put("Error", "Score n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** CALCUL SCORE COLLABORATEUR  ***************************************/
	@SuppressWarnings("rawtypes")
	public JSONObject calculScore(ArrayList mesReponses , int idQuiz , int idCollaborateur) {
		JSONObject jo = new JSONObject();
		int i = 0;
		if(repository.findById(idQuiz).isPresent()) {
			Quiz quiz = repository.findById(idQuiz).get();
			for(Object o : mesReponses) {
				LinkedHashMap maReponse = (LinkedHashMap) o;
				String question = (String) maReponse.get("question");
				String reponse = (String) maReponse.get("reponse");
				
				Optional<Question> uneQuestion = quiz.getListQuestions().stream().filter(q->q.getQuestion().equals(question)).findFirst();
				Optional<Reponse> uneReponse = uneQuestion.get().getListReponses().stream().filter(r->r.getReponse().equals(reponse)).findFirst();
				if(uneReponse.get().isCorrecte()) {
					i = i +1;
				}
			}
			
			float resultat = (i*100) / mesReponses.size();
			Score score = new Score();
			score.setIdCollaborateur(idCollaborateur);
			score.setQuiz(quiz);
			score.setResultat(resultat);
			jo.put("Score", repositoryS.save(score));
			return jo;

		}else {
			jo.put("Error", "Quiz n'existe pas !");
			return jo;
		}
	}
	
	
	/*********************************** API USER BY ID ***************************************/
	public ResponseEntity<JSONObject> getUserAPI(String uri , int id) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
		map.add("id", id);
		HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
		ResponseEntity<JSONObject> response = restTemplate.postForEntity( uri, request , JSONObject.class );
		return response;
	}
	
	
	/*********************************** API FORMATIONS BY COLLABORATEUR  ***************************************/
	public ResponseEntity<JSONObject> getFormationsByCollaborateur(String uri , int id) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, Integer> map= new LinkedMultiValueMap<String, Integer>();
		map.add("id", id);
		HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<MultiValueMap<String, Integer>>(map, headers);
		ResponseEntity<JSONObject> response = restTemplate.postForEntity( uri, request , JSONObject.class );
		return response;
	}
	
	
	
	
	
}
