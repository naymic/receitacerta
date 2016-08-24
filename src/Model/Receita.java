package Model;

import java.util.ArrayList;

import Annotations.Entity;

public class Receita extends Model{
	private Integer id;
	private String nome;
	private Integer tempoPreparo;
	private String rendimento;
	private ReceitaTipos receitaTipos;
	private ReceitaRendimentoTipos receitaRendimentoTipos;
	private Usuario usuario;
	private String caminhoFoto;
	private ArrayList<Pertence> listaPertence;
	private ArrayList<Passo> listaPassos;

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

	@Entity(attributeName = "receita_tipos_id", fk=true)
	public ReceitaTipos dgetReceitaTipos() {
		return receitaTipos;
	}

	public void dsetReceitaTipos(ReceitaTipos receitaTipos) {
		this.receitaTipos = receitaTipos;
	}
	
	@Entity(attributeName = "receita_rendimento_tipos_id", fk=true)
	public ReceitaRendimentoTipos dgetReceitaRendimentoTipos() {
		return receitaRendimentoTipos;
	}

	public void dsetReceitaRendimentoTipos(ReceitaRendimentoTipos receitaRendimentoTipos) {
		this.receitaRendimentoTipos = receitaRendimentoTipos;
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

	@Override
	public String getTableName() {
		return "receitas";
	}
	
	@Entity(attributeName="", fk=true)
	public ArrayList<Pertence> aqgetListaPertence() {
		return listaPertence;
	}

	public void asetListaPertence(ArrayList<Pertence> listaPertence) {
		this.listaPertence = listaPertence;
	}

	@Entity(attributeName="", fk=true)
	public ArrayList<Passo> agetListaPassos() {
		return listaPassos;
	}

	public void asetListaPassos(ArrayList<Passo> listaPassos) {
		this.listaPassos = listaPassos;
	}
	
}
