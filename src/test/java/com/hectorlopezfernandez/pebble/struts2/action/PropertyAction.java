package com.hectorlopezfernandez.pebble.struts2.action;

import com.opensymphony.xwork2.ActionSupport;

public class PropertyAction extends ActionSupport {

	private static final long serialVersionUID = -4486473459933344542L;

	private String name = "Hello!";

	public String execute() throws Exception {
		return SUCCESS;
	}

	public String getName() {
		return name;
	}

}