package model;

import annotations.AModelClasses;
import annotations.Entity;
import jresponseclasses.JReturn;


public abstract class ReceitaParent extends Model {
	private Integer id;
	private String nome;
	private Integer tempoPreparo;
	private String rendimento;
	private Integer receitaRendimentosTipoValor;
	private Usuario usuario;
	private String caminhoFoto;
	
	@Entity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}

	public void dsetId(Integer id) {
		this.id = id;
	}

	@Entity(attributeName = "nome")
	public String dgetNome() {
		return nome;
	}

	public void dsetNome(String nome) {
		this.nome = nome;
	}
	
	@Entity(attributeName = "tempo_preparo")
	public Integer dgetTempoPreparo() {
		return tempoPreparo;
	}

	public void dsetTempoPreparo(Integer tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}

	@Entity(attributeName = "rendimento", required=false)
	public String dgetRendimento() {
		return rendimento;
	}

	public void dsetRendimento(String rendimento) {
		this.rendimento = rendimento;
	}
	
	@Entity(attributeName = "receita_rendimento_tipos_valor")
	public Integer dgetReceitaRendimentosTipoValor() {
		return receitaRendimentosTipoValor;
	}

	public void dsetReceitaRendimentosTipoValor(Integer receitaRendimentoValor) {
		this.receitaRendimentosTipoValor = receitaRendimentoValor;
	}

	@Entity(attributeName = "usuario_id", fk=true)
	public Usuario dgetUsuario() {
		return usuario;
	}

	public void dsetUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Entity(attributeName = "caminho_foto", required=false)
	public String dgetCaminhoFoto() {
		return caminhoFoto;
	}

	public void dsetCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}

}
