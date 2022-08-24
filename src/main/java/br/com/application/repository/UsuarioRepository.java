package br.com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.application.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
	UsuarioEntity findByLogin(String login);
	
}
