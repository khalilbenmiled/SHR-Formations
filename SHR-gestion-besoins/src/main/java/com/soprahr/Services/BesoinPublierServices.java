package com.soprahr.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprahr.Repository.BesoinsPublierRepository;
import com.soprahr.Repository.BesoinsRepository;
import com.soprahr.model.Besoins;
import com.soprahr.model.BesoinsPublier;

import net.minidev.json.JSONObject;

@Service
public class BesoinPublierServices {

	@Autowired
	public BesoinsPublierRepository repository;
	@Autowired
	public BesoinsRepository repositoryB;
	
	/*********************************** AJOUTER UN BESOIN POUR LE PUBLIER ***************************************/
	public JSONObject addBesoinsPublier(int idBesoin) {
		JSONObject jo = new JSONObject();
		if(repositoryB.findById(idBesoin).isPresent()) {
			Besoins besoin = repositoryB.findById(idBesoin).get();
			String nomTheme = besoin.getTheme().getNom();
			int quarter = besoin.getQuarter();
			
			BesoinsPublier besoinPublier = repository.getBesoinsPublierByThemeAndQuarter(nomTheme, quarter);
			if(besoinPublier == null) {
				BesoinsPublier newBesoins = new BesoinsPublier();
				newBesoins.setTheme(nomTheme);
				newBesoins.setPublier(false);
				newBesoins.setQuarter(quarter);
				List<Besoins> listBesoins = new ArrayList<Besoins>();
				listBesoins.add(besoin);
				newBesoins.setListBesoins(listBesoins);
				jo.put("BesoinPublier",repository.save(newBesoins));
				return jo;
			}else {
				List<Besoins> listBesoins = besoinPublier.getListBesoins();
				listBesoins.add(besoin);
				besoinPublier.setListBesoins(listBesoins);
				jo.put("BesoinPublier",repository.save(besoinPublier));
				return jo;
			}
		}else {
			jo.put("Error" , "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** RETIRER UN BESOIN AVANT LE PUBLIER ***************************************/
	public JSONObject retirerBesoinPublier(int idBesoin) {
		JSONObject jo = new JSONObject();
		if(repositoryB.findById(idBesoin).isPresent()) {
			Besoins besoin = repositoryB.findById(idBesoin).get();
			BesoinsPublier besoinPublier = repository.getBesoinsPublierByThemeAndQuarter(besoin.getTheme().getNom(), besoin.getQuarter());
			List<Besoins> listBesoins = besoinPublier.getListBesoins();
			listBesoins.remove(listBesoins.indexOf(besoin));
			if(listBesoins.size() == 0) {
				repository.delete(besoinPublier);
				jo.put("Success" , besoinPublier.getId());
				return jo;
			}else {
				besoinPublier.setListBesoins(listBesoins);
				jo.put("BesoinPublier", repository.save(besoinPublier));
				return jo;
			}
		
		}else {
			jo.put("Error", "Besoin n'existe pas !");
			return jo;
		}
	}
	
	/*********************************** LIST BESOINS A PUBLIER ***************************************/
	public JSONObject listBesoinsPublier() {
		JSONObject jo = new JSONObject();
		if (repository.getAllNotPublish().size() != 0 ) {
			jo.put("BesoinsPublier", repository.getAllNotPublish());
			return jo;
		}else {
			jo.put("Error", "La listes des besoins à publier");
			return jo;
		}
	}
	
	/*********************************** PUBLIER UN BESOIN  ***************************************/
	public JSONObject publierBesoin(int id) {
		JSONObject jo = new JSONObject();
		if( repository.findById(id).isPresent() ) {
			BesoinsPublier besoinPublier = repository.findById(id).get();
			for(Besoins b : besoinPublier.getListBesoins()) {
				b.setPublier(true);
				repositoryB.save(b);
			}
			besoinPublier.setPublier(true);
			jo.put("BesoinPublier", repository.save(besoinPublier));
			return jo;
		}else {
			jo.put("Error", "BesoinPublier n'existe pas !");
			return jo;
		}
	}
	
	
	
	
	
	
	
}
