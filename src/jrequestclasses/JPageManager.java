package jrequestclasses;

public class JPageManager {
	Integer totalPages;
	Integer actualPage;
	Integer linesPerPage;
	
	
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getActualPage() {
		return actualPage;
	}
	public void setActualPage(Integer actualPage) {
		this.actualPage = actualPage;
	}
	public Integer getLinesPerPage() {
		return linesPerPage;
	}
	public void setLinesPerPage(Integer linesPerPage) {
		this.linesPerPage = linesPerPage;
	}
}
