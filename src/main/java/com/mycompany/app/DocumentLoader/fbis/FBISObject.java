package com.mycompany.app.DocumentLoader.fbis;

public class FBISObject {

	private String docNum;
	private String ht;
	private String header;
	private String h2;
	private String date;
	private String h3;
	private String ti;
	private String text;

	public FBISObject() {
		super();
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getHt() {
		return ht;
	}

	public void setHt(String ht) {
		this.ht = ht;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getH2() {
		return this.h2;
	}

	public void setH2(String h2) {
		this.h2 = h2;
	}

	public String getH3() {
		return this.h2;
	}

	public void setH3(String h3) {
		this.h3 = h3;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTi() {
		return ti;
	}

	public void setTi(String ti) {
		this.ti = ti;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "FBISObject [docNum=" + docNum + ", ht=" + ht + ", header=" + header + ", date=" + date + ", ti=" + ti
				+ ", text=" + text + "]";
	}
}
