package br.gov.mg.uberlandia.decserver.dto;

public class LoginEAutorizaDTO {

	private String username;
	private String password;
	private String oidSistemas;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOidSistemas() {
		return oidSistemas;
	}

	public void setOidSistemas(String oidSistemas) {
		this.oidSistemas = oidSistemas;
	}

}
