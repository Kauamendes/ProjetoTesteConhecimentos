package com.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.Entity.PessoaEntity;

public interface PessoaRepository extends JpaRepository<PessoaEntity, Long>{

}
