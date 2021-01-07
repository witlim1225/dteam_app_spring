package com.csslect.app.dto;

public class MemberDTO {
	String id, passwd, name, phonenumber, address;

	// 로그인할때 비밀번호 없이 멤버변수 보낼때
	public MemberDTO(String id, String name, String phonenumber, String address) {
		super();
		this.id = id;
		this.name = name;
		this.phonenumber = phonenumber;
		this.address = address;
	}

	// 데이터베이스에 멤버변수 추가할때
	public MemberDTO(String id, String passwd, String name, String phonenumber, String address) {
		super();
		this.id = id;
		this.passwd = passwd;
		this.name = name;
		this.phonenumber = phonenumber;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
