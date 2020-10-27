package com.projeto.banco.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.projeto.banco.api.model.Banco;

public interface BancoRepository extends MongoRepository<Banco, Integer>{

}
