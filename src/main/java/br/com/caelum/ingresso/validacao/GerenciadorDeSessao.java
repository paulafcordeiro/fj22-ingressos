package br.com.caelum.ingresso.validacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {

	private List<Sessao> listaDeSessoes;

	public GerenciadorDeSessao(List<Sessao> listaDeSessoes) {
		this.listaDeSessoes = listaDeSessoes;
		// TODO Auto-generated constructor stub
	}

	public boolean cabe(Sessao novaSessao) {

		return listaDeSessoes.stream().noneMatch(x -> conflita(x, novaSessao));

	}

	private boolean conflita(Sessao sessao1, Sessao sessao2) {
		LocalDate hoje = LocalDate.now();
		LocalDateTime inicioSessao1 = sessao1.getHorario().atDate(hoje);
		LocalDateTime inicioSessao2 = sessao2.getHorario().atDate(hoje);
		LocalDateTime fimSessao1 = inicioSessao1.plus(sessao1.getFilme().getDuracao());
		LocalDateTime fimSessao2 = inicioSessao2.plus(sessao1.getFilme().getDuracao());
		boolean sessao1ComecaAntesDaSessao2 = inicioSessao1.isBefore(inicioSessao2);

		if (sessao1ComecaAntesDaSessao2) {
			return fimSessao1.isAfter(inicioSessao2);
		} else {
			return fimSessao2.isAfter(inicioSessao1);
		}

	}

}
