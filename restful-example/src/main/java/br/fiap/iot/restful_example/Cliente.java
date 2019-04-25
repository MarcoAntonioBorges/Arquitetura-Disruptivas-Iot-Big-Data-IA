package br.fiap.iot.restful_example;

import java.io.Serializable;
import java.util.Date;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 7290176776484525708L;
	
	private long id;
	private Date nascimento;
	private String nome;
	private int cpf;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getNascimento() {
		return nascimento;
	}
	
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	
	public int getCpf() {
		return cpf;
	}
	
	public void setCpf(int cpf) {
		this.cpf = cpf;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
