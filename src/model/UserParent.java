package model;

import annotations.AModelClasses;
import annotations.AEntity;
import interfaces.IUser;
import jresponseclasses.JReturn;


public class UserParent extends Model implements IUser {

	Integer id;
	String nome;
	String caminhoFoto;
	boolean loggedin;
	
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "usuario";
	}

	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub

	}
	
	
	@AEntity(attributeName = "id", pk=true)
	public Integer dgetId() {
		return id;
	}


	public void dsetId(Integer id) {
		this.id = id;
	}
	
	@AEntity(attributeName = "caminho_foto", required = false)
	public String dgetCaminhoFoto() {
		return caminhoFoto;
	}


	public void dsetCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
		
	}
	
	
	
	@AEntity(attributeName = "nome", required = false)
	public String dgetNome() {
		return nome;
	}


	public void dsetNome(String nome) {
		this.nome = nome;
	}
	
	
	/* 
	 * IUser Interface Methods
	 * @see Interfaces.IUser
	 */

	

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return this.dgetId();
	}


	@Override
	public void setId(Integer id) {
		this.dsetId(id);		
	}


	@Override
	public boolean isLoggedin() {
		return this.loggedin;
	}


	@Override
	public void setLoggedin(boolean loggedin) {
		this.loggedin=loggedin;
	}


	@Override
	public String getUsername() {
		return this.dgetNome();
	}


	@Override
	public void setUsername(String username) {
		this.dsetNome(username);
	}


	@Override
	public String getImagepath() {
		return this.dgetCaminhoFoto();
	}


	@Override
	public void setImagepath(String imagepath) {
		this.dsetCaminhoFoto(imagepath);
	}


	@Override
	public void setSelf(IUser iu) {
		this.setId(iu.getId());
		this.setLoggedin(iu.isLoggedin());
		this.setUsername(iu.getUsername());
		this.setImagepath(iu.getImagepath());
	}

}
