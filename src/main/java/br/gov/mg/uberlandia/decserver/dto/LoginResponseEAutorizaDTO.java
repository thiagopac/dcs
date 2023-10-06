package br.gov.mg.uberlandia.decserver.dto;

public class LoginResponseEAutorizaDTO {

	private boolean sucesso;
	private InformacoesLoginEAutorizaDTO informacoesUsuario;
	private String mensagem;
	private String token;

	public InformacoesLoginEAutorizaDTO getInformacoesUsuario() {
		return informacoesUsuario;
	}

	public void setInformacoesUsuario(InformacoesLoginEAutorizaDTO informacoesUsuario) {
		this.informacoesUsuario = informacoesUsuario;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
