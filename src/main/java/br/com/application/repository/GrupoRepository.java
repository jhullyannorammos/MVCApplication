package br.com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.application.entity.GrupoEntity;
import br.com.application.entity.UsuarioEntity;

@Repository
public interface GrupoRepository extends JpaRepository<GrupoEntity, Long>{

	List<GrupoEntity> findByUsuariosIn(UsuarioEntity usuarioEntity);
	
}
