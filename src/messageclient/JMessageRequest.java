package messageclient;

public class JMessageRequest {

	private String apptoken;
	private Integer appid;
	private Integer modulid;
	
	
	
	
	
	
	/*** Getters && Setters ***/
	
	public String getApptoken() {
		return apptoken;
	}
	public void setApptoken(String apptoken) {
		this.apptoken = apptoken;
	}
	public Integer getAppid() {
		return appid;
	}
	public void setAppid(Integer appid) {
		this.appid = appid;
	}
	public Integer getModulid() {
		return modulid;
	}
	public void setModulid(Integer modulid) {
		this.modulid = modulid;
	}
	public String getApplang() {
		return applang;
	}
	public void setApplang(String applang) {
		this.applang = applang;
	}
	public JMessageRequestItem[] getRequitems() {
		return requitems;
	}
	public void setRequitems(JMessageRequestItem[] requitems) {
		this.requitems = requitems;
	}
	private String applang;
	private JMessageRequestItem[] requitems; 
	
}
