package Utils;

public class toHtml {

	
	public static String replace(String json){
		
		json = json.replace("à", "&agrave;");
		json = json.replace("ó", "&oacute;");
		json = json.replace("õ", "&otilde;");
		json = json.replace("é", "&eacute;");
		json = json.replace("é", "&eacute;");
		json = json.replace("ê", "&ecirc;");
		
		return json;
		
	}
	
}
