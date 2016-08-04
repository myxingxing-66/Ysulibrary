package com.example.ysulibrary.bean;

public class User {
	private boolean logined;//是否登录
	private String name;//姓名
	private String password;//密码
	private String number;//学号
	private String expiry_date;//失效日期
	private String effective_date;//生效日期
	private String biggest_lend_num;//最大可借图书
	private String biggest_absord_num;//最多可预约
	private String reader_type;//读者类型
	private String lended_num;//累计借阅
	private String violations;//违章次数
	private String sex;//性别
	private String phone;//手机号
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(String effective_date) {
		this.effective_date = effective_date;
	}

	public String getBiggest_lend_num() {
		return biggest_lend_num;
	}

	public void setBiggest_lend_num(String biggest_lend_num) {
		this.biggest_lend_num = biggest_lend_num;
	}

	public String getBiggest_absord_num() {
		return biggest_absord_num;
	}

	public void setBiggest_absord_num(String biggest_absord_num) {
		this.biggest_absord_num = biggest_absord_num;
	}

	public String getReader_type() {
		return reader_type;
	}

	public void setReader_type(String reader_type) {
		this.reader_type = reader_type;
	}

	public String getLended_num() {
		return lended_num;
	}

	public void setLended_num(String lended_num) {
		this.lended_num = lended_num;
	}

	public String getViolations() {
		return violations;
	}

	public void setViolations(String violations) {
		this.violations = violations;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public User(){
		this.logined = false;
	}
	
	public boolean isLogined() {
		return logined;
	}
	public void setLogined(boolean logined) {
		this.logined = logined;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
}
