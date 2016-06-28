package Model;

import Interfaces.IApplicationSession;
import Reflection.Entity;
import Utils.CryptString;
import Utils.StringUtils;
import Utils.Return;

public class Usuario extends Model {

	Integer id;
	String email;
	String senha;
	String celular;
	String nome;
	String sobrenome;
	String caminhoFoto;
	
	
	@Override
	public String getTableName() {
		return "usuario";
	}
	
	
	public Return verify(Return r){
		r = super.verify(r);
		
		this.celular = StringUtils.justNumbers(this.celular);
		
		if(this.dgetCelular().length() < 12 && this.dgetCelular().length() > 12){
			r.addAttributeError(this.getClass().getSimpleName(), "celular", "Please use only number from 0-9 and string lenght exactly 12 like 062995652132");
		}
		
		if (!this.dgetEmail().contains("@") || !this.dgetEmail().contains(".")){
			r.addAttributeError(this.getClass().getSimpleName(), "email", "This is not a valid email Adress. Please enter a valid address!");
		}
		
		return r;
	}
	
	

	@Entity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}


	public void dsetId(Integer id) {
		this.id = id;
	}

	@Entity(attributeName = "email")
	public String dgetEmail() {
		return email;
	}


	public void dsetEmail(String email) {
		this.email = email;
	}

	@Entity(attributeName = "senha")
	public String dgetSenha() {
		return senha;
	}


	public void dsetSenha(String senha) {
		
		
		if(senha.length() < 40 ){
			//Crypting Password
			try {
				senha = CryptString.crypt(senha);
			} catch (Exception e) {

			}
		}

		this.senha = senha;
	}

	@Entity(attributeName = "celular", required = false)
	public String dgetCelular() {
		return celular;
	}


	public void dsetCelular(String celular) {
		this.celular = celular;
	}

	
	@Entity(attributeName = "nome", required = false)
	public String dgetNome() {
		return nome;
	}


	public void dsetNome(String nome) {
		this.nome = nome;
	}

	@Entity(attributeName = "sobrenome", required = false)
	public String dgetSobrenome() {
		return sobrenome;
	}


	public void dsetSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	@Entity(attributeName = "caminho_foto", required = false)
	public String dgetCaminhoFoto() {
		return caminhoFoto;
	}


	public void dsetCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}
	
	

}
