package com.soprahr.Services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.soprahr.Repository.DocsRepository;
import com.soprahr.models.Docs;

import net.minidev.json.JSONObject;



@Service
public class UploadService {

	@Autowired
	public DocsRepository repository;
	
	

    
	public JSONObject saveDocs(MultipartFile file , String nom , String description) {
		JSONObject jo = new JSONObject();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			Docs doc = new Docs(fileName,file.getContentType(), nom , description ,file.getBytes());
			jo.put("Docs",repository.save(doc));
			return jo;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Docs getFile(int fileId) {
		return repository.findById(fileId).get();
	}
	
	
	public List<Docs> getFiles() {
		return  repository.findAll();
	}
	
	
	public JSONObject deleteDocs(int id) {
		JSONObject jo = new JSONObject();
		if(repository.findById(id).isPresent()) {
			repository.delete(repository.findById(id).get());
			jo.put("Success", "Document supprimé");
			return jo;
		}else {
			jo.put("Error" , "Document n'existe pas !");
			return jo;
		}
	}
	
	
	
}
