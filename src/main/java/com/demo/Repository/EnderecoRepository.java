package com.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.Entity.EnderecoEntity;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

}
