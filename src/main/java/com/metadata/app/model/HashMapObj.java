package com.metadata.app.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class HashMapObj {
	@Id
	private TabCol tabColNam;
	private Column col;
	public TabCol getTabColNam() {
		return tabColNam;
	}
	public void setTabColNam(TabCol tabColNam) {
		this.tabColNam = tabColNam;
	}
	public Column getCol() {
		return col;
	}
	public void setCol(Column col) {
		this.col = col;
	}
	@Override
	public String toString() {
		return "HashMapObj [tabColNam=" + tabColNam + ", col=" + col + "]";
	}

	

}
