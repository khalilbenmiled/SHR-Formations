package com.soprahr.Services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.soprahr.Repository.DocsRepository;
import com.soprahr.models.Docs;



@Service
public class UploadService {

	@Autowired
	public DocsRepository repository;
	
	

    
	public Docs saveDocs(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			Docs doc = new Docs(fileName,file.getContentType(),file.getBytes());
			return repository.save(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Docs getFile(int fileId) {
		return repository.findById(fileId).get();
	}
	
	
	public List<Docs> getFiles() {
		return repository.findAll();
	}
}
