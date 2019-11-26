package com.metadata.app.model;

import java.io.Serializable;

public class Column implements Serializable{

	private static final long serialVersionUID = 1L;
	private String colNam;
	private String dataTyp;
	public String getColNam() {
		return colNam;
	}
	public void setColNam(String colNam) {
		this.colNam = colNam;
	}
	public String getDataTyp() {
		return dataTyp;
	}
	public void setDataTyp(String dataTyp) {
		this.dataTyp = dataTyp;
	}
	@Override
	public String toString() {
		return "Column [colNam=" + colNam + ", dataTyp=" + dataTyp + "]";
	}
}
