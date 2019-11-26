package com.metadata.app.model;

import java.io.Serializable;

public class TabCol implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String tColName;
	public TabCol(String tColName) {
		super();
		this.tColName = tColName;
	}
	@Override
	public String toString() {
		return tColName;
	}

}