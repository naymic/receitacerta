package model;

import annotations.Entity;



public class TestCity extends Model{
	Integer id;
	String name;
	String country;
	String state;
	String type;
	String notice;
	
	
	@Entity(attributeName = "id", pk = true)
	public Integer dgetId() {
		return id;
	}
	public void dsetId(Integer id) {this.id = id;}
	
	@Entity(attributeName = "name")
	public String dgetName() {
		return name;
	}
	public void dsetName(String name) {this.name = name;}
	

	@Entity(attributeName = "country")
	public String dgetCountry() {
		return country;
	}
	public void dsetCountry(String country) {
		this.country = country;
	}

	
	@Entity(attributeName = "state")
	public String dgetState() {
		return state;
	}
	public void dsetState(String state) {this.state = state;}

	
	@Entity(attributeName = "type")
	public String dgetType() {
		return type;
	}
	public void dsetType(String type) {this.type = type;}
	
	
	@Entity(attributeName = "notice", required = false)
	public String dgetNotice() {	
		return notice;
	}
	public void dsetNotice(String notice) {this.notice = notice;}
	

	@Override
	public String getTableName() {
		return "city";
	}

}
