package model;

import java.util.ArrayList;

import annotations.AModelClasses;
import annotations.Entity;
import interfaces.IApplicationSession;
import interfaces.IUser;
import jresponseclasses.JReturn;
import utils.CryptString;
import utils.StringUtils;
@AModelClasses(needUserObject = false)
public class Usuario extends UserParent {


	String email;
	String senha;
	String celular;
	
	String sobrenome;
	
	
	String oldSenha;
	String newSenha;
	String repeatedSenha;
	
	
	
	public void verify(JReturn r){
		
		try{
			
			this.celular = StringUtils.justNumbers(this.celular);
		
			if(this.dgetCelular().length() < 12 && this.dgetCelular().length() > 12){
				r.addAttributeError(this.getClass().getSimpleName(), "celular", "32");//Please use only number from 0-9 and string lenght exactly 12 like 062995652132
			}
			
			if (!this.dgetEmail().contains("@") || !this.dgetEmail().contains(".")){
				r.addAttributeError(this.getClass().getSimpleName(), "email", "33");//This is not a valid email Adress. Please enter a valid address!
			}
		}catch(NullPointerException npe){
			
		}
		
		
	}
	

	@Entity(attributeName = "email")
	public String dgetEmail() {
		return email;
	}


	public void dsetEmail(String email) {
		this.email = email;
	}

	@Entity(attributeName = "senha", required=false)
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

	@Entity(attributeName = "sobrenome", required = false)
	public String dgetSobrenome() {
		return sobrenome;
	}


	public void dsetSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	
	/* Special Attributes for add and edit a user */
	


	@Entity(attributeName = "", required = false, isInDB=false)
	public String getOldSenha() {
		return oldSenha;
	}


	public void setOldSenha(String oldSenha) {
		this.oldSenha = oldSenha;
	}

	@Entity(attributeName = "", required = false, isInDB=false)
	public String getNewSenha() {
		return newSenha;
	}


	public void setNewSenha(String newSenha) {
		this.newSenha = newSenha;
	}
	
	@Entity(attributeName = "", required = false, isInDB=false)
	public String getRepeatedSenha() {
		return repeatedSenha;
	}


	public void setRepeatedSenha(String repeatedSenha) {
		this.repeatedSenha = repeatedSenha;
	}
	
	

}
