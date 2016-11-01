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
	private User user;
	private String caminhoFoto;
	private String stringIngredientesId;

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
	public User dgetUser() {
		return user;
	}

	public void dsetUser(User usuario) {
		this.user = usuario;
	}

	@Entity(attributeName = "caminho_foto", required=false)
	public String dgetCaminhoFoto() {
		return caminhoFoto;
	}

	public void dsetCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}

	//Not a attribute in the table, but used to get the ingredients list
	@Entity(attributeName="", isInDB=false)
	public String getStringIngredientesId() {
		return stringIngredientesId;
	}

	public void setStringIngredientesId(String stringIngredientesId) {
		this.stringIngredientesId = stringIngredientesId;
	}



}
