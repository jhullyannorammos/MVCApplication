package br.com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.application.entity.GrupoEntity;
import br.com.application.entity.PermissaoEntity;

public interface PermissaoRepository extends JpaRepository<PermissaoEntity, Long> {

	List<PermissaoEntity> findByGruposIn(GrupoEntity grupoEntity);
}
