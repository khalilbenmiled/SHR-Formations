package com.soprahr.API;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprahr.Services.BesoinsService;
import com.soprahr.Services.ReportingBesoinsService;
import com.soprahr.model.Besoins;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/besoins")
public class BesoinsAPI {

	@Autowired
	public BesoinsService service;
	@Autowired
	public ReportingBesoinsService reporting;
	@PersistenceContext
	public EntityManager em;
	
	@PostMapping(value = "/addByCollaborateur" , produces = MediaType.APPLICATION_JSON_VALUE )
	public JSONObject addBesoinByCollaborateur(@RequestBody Besoins besoin) {
		return service.addBesoinByCollaborateur(besoin);
	}
	
	@PostMapping(value = "/modifier" , produces = MediaType.APPLICATION_JSON_VALUE )
	public JSONObject modifierBesoin(@RequestBody Besoins besoin) {
		return service.modifierBesoin(besoin);
	}
	
	@PostMapping(value = "/test" ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
	public int test(@Param(value = "quarter") int quarter) {
		return service.getAnnee(quarter);
	}
	
	
	@PostMapping(value = "/addByTL" , produces = MediaType.APPLICATION_JSON_VALUE  )
	public JSONObject addBesoinByTeamLead(@RequestBody Besoins besoin) {
		return service.addBesoinByTeamLead(besoin);
	}

	@PostMapping(value = "/rapports" , produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject rapportsBesoins(
			@Param(value ="nomTheme") String nomTheme , @Param(value = "typeTheme") String typeTheme , @Param(value = "quarter") int quarter , @Param(value ="idProjet") int idProjet , @Param(value ="validerTL") String validerTL , @Param(value ="validerMG") String validerMG , @Param(value ="bu") String bu
			) {
		return service.rapportsBesoins(nomTheme, typeTheme, quarter, idProjet, validerTL, validerMG,bu);
	}
	
	@PostMapping(value = "/rapportsTL" , produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject rapportsBesoinsByTL(
			@Param(value ="idTL") int idTL , @Param(value ="nomTheme") String nomTheme , @Param(value = "typeTheme") String typeTheme , @Param(value = "quarter") int quarter , @Param(value ="idProjet") int idProjet , @Param(value ="validerTL") String validerTL , @Param(value ="validerMG") String validerMG
			) {
		return service.rapportsBesoinsByTL(idTL, nomTheme, typeTheme, quarter, idProjet, validerTL, validerMG);
	}

	@PostMapping(value = "/rapportsMG" , produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject rapportsBesoinsByMG(
			@Param(value ="idManager") int idManager , @Param(value ="nomTheme") String nomTheme , @Param(value = "typeTheme") String typeTheme , @Param(value = "quarter") int quarter , @Param(value ="idProjet") int idProjet , @Param(value ="validerMG") String validerMG
			) {
		return service.rapportsBesoinsByMG(idManager, nomTheme, typeTheme, quarter, idProjet, validerMG);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllBesoins() {
		return service.getAllBesoins();
	}
	

	@PostMapping( value = "/remove" ,produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject deleteBesoin(@Param(value = "id") int id) {
		return service.deleteBesoin(id);
	}
	
	@PostMapping( value = "/removeByCollaborateur" ,produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject deleteBesoinByCollaborateur(@Param(value = "id") int id) {
		return service.deleteBesoinByCollaborateur(id);
	}
	
	@PostMapping( value = "/removeByManagerOrSf" ,produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject deleteBesoinByManagerOrSF(@Param(value = "id") int id) {
		return service.deleteBesoinByManagerOrSF(id);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getBesoinById(@PathParam(value = "id") int id) {
		return service.getBesoinById(id);
	}
	
	@PostMapping(value = "/byTL", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getBesoinsByTL(@Param(value = "id") int id) {
		return service.getBesoinsByTL(id);
	}
	
	@PostMapping(value = "/byMG", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getBesoinsByMG(@Param(value = "id") int id) {
		return service.getBesoinsByManager(id);
	}

	@PostMapping(value = "/byUser", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getBesoinsByUser(@Param(value = "id") int id) {
		return service.getBesoinsByUser(id);
	}
	
	@PostMapping(value = "/valider", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject validerBesoinTL(@Param(value = "idBesoin") int idBesoin , @Param(value = "trimestre")int trimestre , @Param(value ="idProjet") int idProjet , @Param(value ="validerMG") boolean validerMG ) {
		return service.validerBesoinTL(idBesoin, trimestre, idProjet , validerMG);
	}
	
	@PostMapping(value = "/validerMG", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject validerBesoinMG(@Param(value = "idBesoin") int idBesoin ) {
		return service.validerBesoinMG(idBesoin);
	}
	
	@PostMapping(value = "/annulerValidationTL", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject annulerValidationBesoin(@Param(value = "idBesoin") int idBesoin ) {
		return service.annulerValidationBesoin(idBesoin);
	}
	
	@PostMapping(value = "/annulerValidationMG", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject annulerValidationBesoinMG(@Param(value = "idBesoin") int idBesoin ) {
		return service.annulerBesoinMG(idBesoin);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/setPlanifier", produces = MediaType.APPLICATION_JSON_VALUE )
	public JSONObject setBesoinPlanifier(@RequestBody JSONObject input ) {
		
		String idBesoin = input.getAsString("id");
		ArrayList arrayParticipants = (ArrayList) input.get("listParticipants");
		
	
		return service.setBesoinPlanifier(Integer.parseInt(idBesoin) , arrayParticipants);
	}
	
	@PostMapping(value = "/listParticipants", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getListParticipantFormation(@Param(value = "theme") String theme , @Param(value ="quarter") int quarter) {
		return service.getListParticipantFormation(theme, quarter);
	}
	
	@PostMapping(value = "/listParticipantsBesoins", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getListParticipantBesoin(@Param(value = "id") int id) {
		return service.getListParticipantBesoin(id);
	}
	
	@PostMapping(value = "/deleteBP", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject deleteBesoinFromBesoinPublier(@Param(value = "idB") int idB,@Param(value="idBP") int idBP) {
		return service.deleteBesoinFromBesoinPublier(idB,idBP);
	}
	
	@PostMapping(value = "/reporting/byFilter", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject filterFormationDemander(@Param(value = "bu") String bu,@Param(value = "quarter") int quarter , @Param(value = "theme") String theme , @Param(value = "annee") int annee) {
		return reporting.filterFormationDemander(bu,quarter,theme,annee);
	}
	
	@PostMapping(value = "/reporting/byFilterCollaborateur", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject filterFormationDemanderParCollaborateur(@Param(value = "bu") String bu,@Param(value = "quarter") int quarter , @Param(value = "theme") String theme , @Param(value = "id") int id , @Param(value = "annee") int annee) {
		return reporting.filterFormationDemanderParCollaborateur(bu,quarter,theme,id,annee);
	}
	
	@PostMapping(value = "/reporting/byFilterTeamLead", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject filterFormationDemanderParTeamLead(@Param(value = "bu") String bu,@Param(value = "quarter") int quarter , @Param(value = "theme") String theme , @Param(value = "id") int id , @Param(value = "annee") int annee) {
		return reporting.filterFormationDemanderParTeamLead(bu,quarter,theme,id,annee);
	}
	
	@PostMapping(value = "/reporting/byFilterManager", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject filterFormationDemanderParManager(@Param(value = "bu") String bu,@Param(value = "quarter") int quarter , @Param(value = "theme") String theme , @Param(value = "id") int id , @Param(value = "annee") int annee) {
		return reporting.filterFormationDemanderParManager(bu,quarter,theme,id,annee);
	}
	
	
	@PostMapping(value = "/reporting/byTypeTheme", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getBesoinsByThemeType(@Param(value = "type") String type) {
		return reporting.getBesoinsByThemeType(type);
	}
	
	@PostMapping(value = "/reporting/byProjet", produces = MediaType.APPLICATION_JSON_VALUE ,  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public JSONObject getBesoinsByProjet(@Param(value = "projet") String projet) {
		return reporting.getBesoinsByProjet(projet);
	}
	
	
	
	
	

	
}
