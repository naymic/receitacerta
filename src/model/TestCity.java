package model;

import annotations.AModelClasses;
import annotations.AEntity;
import jresponseclasses.JReturn;


@AModelClasses(needUserObject = false)
public class TestCity extends Model{
	Integer id;
	String name;
	String country;
	String state;
	String type;
	String notice;
	
	
	@AEntity(attributeName = "id", pk = true)
	public Integer dgetId() {
		return id;
	}
	public void dsetId(Integer id) {this.id = id;}
	
	@AEntity(attributeName = "name")
	public String dgetName() {
		return name;
	}
	public void dsetName(String name) {this.name = name;}
	

	@AEntity(attributeName = "country")
	public String dgetCountry() {
		return country;
	}
	public void dsetCountry(String country) {
		this.country = country;
	}

	
	@AEntity(attributeName = "state")
	public String dgetState() {
		return state;
	}
	public void dsetState(String state) {this.state = state;}

	
	@AEntity(attributeName = "type")
	public String dgetType() {
		return type;
	}
	public void dsetType(String type) {this.type = type;}
	
	
	@AEntity(attributeName = "notice", required = false)
	public String dgetNotice() {	
		return notice;
	}
	public void dsetNotice(String notice) {this.notice = notice;}
	

	@Override
	public String getTableName() {
		return "city";
	}
	
	@Override
	public void verify(JReturn r) {
		// TODO Auto-generated method stub
		
	}

}
