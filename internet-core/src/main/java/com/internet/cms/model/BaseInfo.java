package com.internet.cms.model;

public class BaseInfo {
	private String name;
	private String address;
	private String zipCode;
	private String recordCode;
	private String phone;
	private String email;
	private String domainName;
	private int indexPicWidth;
	private int indexPicHeight;
	private int indexPicNumber;
	private String smtp;
	private String port;
	private String businessMail;
	private String mailPwd;

	public int getIndexPicNumber() {
		return indexPicNumber;
	}

	public void setIndexPicNumber(int indexPicNumber) {
		this.indexPicNumber = indexPicNumber;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIndexPicWidth() {
		return indexPicWidth;
	}

	public void setIndexPicWidth(int indexPicWidth) {
		this.indexPicWidth = indexPicWidth;
	}

	public int getIndexPicHeight() {
		return indexPicHeight;
	}

	public void setIndexPicHeight(int indexPicHeight) {
		this.indexPicHeight = indexPicHeight;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getBusinessMail() {
		return businessMail;
	}

	public void setBusinessMail(String businessMail) {
		this.businessMail = businessMail;
	}

	public String getMailPwd() {
		return mailPwd;
	}

	public void setMailPwd(String mailPwd) {
		this.mailPwd = mailPwd;
	}

	@Override
	public String toString() {
		return "BaseInfo [name=" + name + ", address=" + address + ", zipCode="
				+ zipCode + ", recordCode=" + recordCode + ", phone=" + phone
				+ ", email=" + email + ", indexPicWidth=" + indexPicWidth
				+ ", indexPicHeight=" + indexPicHeight + "]";
	}
}
