package br.com.application.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name="tb_grupo", schema="mvcapplication")
@Entity
public class GrupoEntity implements Serializable {

	//@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_id_grupo") @SequenceGenerator(name="seq_id_grupo", sequenceName="seq_id_grupo")  	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_grupo")
	private Long codigo;
	
	@Column(name="ds_nome")
	private String nome;
	
	@Column(name="ds_descricao")
	private String descricao;

	@ManyToMany
	@JoinTable(name="TB_USUARIO_X_GRUPO",
			joinColumns=@JoinColumn(name="id_grupo", referencedColumnName="id_grupo"),
			inverseJoinColumns=@JoinColumn(name="id_usuario", referencedColumnName="id_usuario"))
	private List<UsuarioEntity> usuarios;
	
	@ManyToMany
	@JoinTable(name="TB_PERMISSAO_X_GRUPO", joinColumns=@JoinColumn(name="id_grupo", referencedColumnName="id_grupo"),
			inverseJoinColumns=@JoinColumn(name="id_permissao", referencedColumnName="id_permissao"))
	private List<PermissaoEntity> permissoes;
	
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<UsuarioEntity> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioEntity> usuarios) {
		this.usuarios = usuarios;
	}

	public List<PermissaoEntity> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<PermissaoEntity> permissoes) {
		this.permissoes = permissoes;
	}

}
