package com.projeto.banco.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.banco.api.model.Banco;
import com.projeto.banco.api.repository.BancoRepository;

@RestController
public class BancoController {
	
	@Autowired
	private BancoRepository repository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addbanco")
	public String saveBanco(@RequestBody Banco banco) {
		repository.save(banco);
		return "Adicionado o banco com o código: " + banco.getId();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/findallbancos")
	public List<Banco> getBancos() {
		return repository.findAll();
	}
	
	@GetMapping("/findallbancos/{id}")
	public Optional<Banco> getAllById(@PathVariable int id) {
		return repository.findById(id);
	}
	
	@GetMapping("/delete/{id}")
	public String deleteBanco(@PathVariable int id) {
		repository.deleteById(id);
		return "O banco foi exluido pelo código: " + id;
	}
	
	@PostMapping("/update/{id}")
	public String updateBanco(@PathVariable int id, @RequestBody Banco banco) {
		
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		
		mongoTemplate.findAndModify(query, update.update("nome", banco.getNome()), new FindAndModifyOptions().returnNew(true).upsert(false), Banco.class);
		mongoTemplate.findAndModify(query, update.update("agencia", banco.getAgencia()), new FindAndModifyOptions().returnNew(true).upsert(false), Banco.class);
		mongoTemplate.findAndModify(query, update.update("conta", banco.getConta()), new FindAndModifyOptions().returnNew(true).upsert(false), Banco.class);
		
		return "O banco foi atualizado pelo código: " + banco.getId();
		
	}

}
