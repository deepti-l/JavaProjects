package com.dataextraction.model;

import java.util.List;

public class GroupResponse<T> {
	private boolean isSuccess;
	private String error;
	private Exception exceptio;
	private List<T> items;

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Exception getExceptio() {
		return exceptio;
	}

	public void setExceptio(Exception exceptio) {
		this.exceptio = exceptio;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

}
