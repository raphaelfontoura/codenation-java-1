package br.com.codenation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Time {
	
	private Long id;
	private String nome;
	private LocalDate dataCriacao;
	private String corUniformePrincipal;
	private String corUniformeSecundario;
	private List<Long> jogadores = new ArrayList<>();
	private Long idCapitao;
	
	public Time(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal,
			String corUniformeSecundario) {
		this.id = id;
		this.nome = nome;
		this.dataCriacao = dataCriacao;
		this.corUniformePrincipal = corUniformePrincipal;
		this.corUniformeSecundario = corUniformeSecundario;
	}

	public Long getId() {
		return id;
	}

//	public void setId(Long id) {
//		Time.id = id;
//	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getCorUniformePrincipal() {
		return corUniformePrincipal;
	}

	public void setCorUniformePrincipal(String corUniformePrincipal) {
		this.corUniformePrincipal = corUniformePrincipal;
	}

	public String getCorUniformeSecundario() {
		return corUniformeSecundario;
	}

	public void setCorUniformeSecundario(String corUniformeSecundario) {
		this.corUniformeSecundario = corUniformeSecundario;
	}
	

	public List<Long> getJogadores() {
		List<Long> clone = new ArrayList<Long>(jogadores);
		//List<Long> retornar = jogadores;
		return clone;
	}
	
	public void addJogador(Long idJogador) {
		this.jogadores.add(idJogador);
	}

	public void setIdCapitao(Long idJogador) {
		this.idCapitao = idJogador;
	}

	public Long getIdCapitao() {
		return idCapitao;
	}
	
	@Override
	public String toString() {
		return "Time: "+nome+" data: "+dataCriacao+" cor principal: "+corUniformePrincipal;
	}
	
}
