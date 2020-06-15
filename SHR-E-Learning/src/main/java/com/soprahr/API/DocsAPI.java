package com.soprahr.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.soprahr.Services.UploadService;
import com.soprahr.models.Docs;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(value = "/docs")

public class DocsAPI {

	@Autowired
	public UploadService service;
	
	
	@GetMapping("/")
	public JSONObject getFiles() {
		JSONObject jo = new JSONObject();
		if(service.getFiles().size() != 0 ) {
			jo.put("Docs", service.getFiles());
			return jo;
		}else {
			jo.put("Error", "La list est vide");
			return jo;
		}
	}
	
	@DeleteMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteDocs(@PathVariable(value="id") int id) {
		return service.deleteDocs(id);
	}
	
	@PostMapping(value="/uploadFile")
	public JSONObject uploadMultipleFiles(@RequestParam("file") MultipartFile file , @RequestParam("nom") String nom , @RequestParam("description") String description) {	
		return service.saveDocs(file ,nom , description);
	}
	
	@GetMapping("/downloadFile/{fileId:.+}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable int fileId) {
		Docs doc = service.getFile(fileId);
		
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getDocName() + "\"")
                .body(new ByteArrayResource(doc.getData()));
	}
}
