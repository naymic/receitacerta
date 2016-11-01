package model;

import java.util.HashMap;

import annotations.AModelClasses;

import annotations.Entity;
import jresponseclasses.JReturn;
@AModelClasses(needUserObject = false)
public class Describe extends Model{
	
	String name;
	HashMap<String, Object> annotations;
	
	@Entity(attributeName="", isInDB=false)
	public String dgetName() {
		return name;
	}

	public void dsetName(String name) {
		this.name = name;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void verify(JReturn r) {
		return;
	}
	
	
	public HashMap<String, Object> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(HashMap<String, Object> annotations) {
		this.annotations = annotations;
	}

	
	
}