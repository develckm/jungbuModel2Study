package model2_study.com.dto;

import java.util.Date;

/*
+---------+--------------+------+-----+-------------------+-------------------+
| Field   | Type         | Null | Key | Default           | Extra             |
+---------+--------------+------+-----+-------------------+-------------------+
| user_id | varchar(255) | NO   | PRI | NULL              |                   |
| name    | varchar(255) | NO   |     | NULL              |                   |
| pw      | varchar(255) | NO   |     | NULL              |                   |
| phone   | varchar(255) | NO   | UNI | NULL              |                   |
| email   | varchar(255) | NO   | UNI | NULL              |                   |
| birth   | date         | NO   |     | NULL              |                   |
| signup  | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
+---------+--------------+------+-----+-------------------+-------------------+
 * */
public class UserDto {
//싱글톤패턴(디자인 패턴) : 접근지정자와  get set 함수를 정의해 접근의 제어하는 것 (vo,dto)
	private String userId; //user_id
	//db가 대소문자를 구분하지 않는 경우 언더바 표기법을 사용하는데 자바의 변수 규칙은 낙타표기법이라 lib 적용 시 문제가 생길 수 있다.
	private String name;   
	private String pw;     
	private String phone;  
	private String email; 
	private Date birth; 
	private Date signup;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
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
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Date getSignup() {
		return signup;
	}
	public void setSignup(Date signup) {
		this.signup = signup;
	}
	@Override
	public String toString() {
		return "\"user\":{ \"userId\":\"" + userId + "\",\"name\":\"" + name + "\",\"pw\":\"" + pw + "\",\"phone\":\""
				+ phone + "\",\"email\":\"" + email + "\",\"birth\":\"" + birth + "\",\"signup\":\"" + signup + "\" }";
	} 
	
}
