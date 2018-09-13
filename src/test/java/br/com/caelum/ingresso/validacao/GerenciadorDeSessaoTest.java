package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {
	
	private Filme rogueOne;
	private Sala sala3D;
	private Sessao sessaoDasDez;
	private Sessao sessaoDasTreze;
	private Sessao sessaoDasDezoito;
	
	@Before
	public void preparaSessoes(){
		this.rogueOne = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		this.sala3D = new Sala("Sala 3D", BigDecimal.TEN);
		
		this.sessaoDasDez = new Sessao(LocalTime.parse("10:00:00"),rogueOne, sala3D);
		this.sessaoDasTreze = new Sessao(LocalTime.parse("13:00:00"),rogueOne, sala3D);
		this.sessaoDasDezoito = new Sessao(LocalTime.parse("18:00:00"),rogueOne, sala3D);
	}
	
	@Test
	public void deveConflitarQuandoOInicioDaAtualEstaDentroDaAntiga() {
		// Setup
		Filme filme = new Filme("Matriz", Duration.ofMinutes(180), "Ação", BigDecimal.ONE);
		Sala sala = new Sala("Legal", BigDecimal.TEN);
		Sessao novaSessao = new Sessao(LocalTime.parse("15:00"), filme, sala);
		Sessao sessaoAntiga = new Sessao(LocalTime.parse("14:00"), filme, sala);
		
		
		List<Sessao> listaDeSessoes = new ArrayList<>();
		listaDeSessoes.add(sessaoAntiga);
		
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(listaDeSessoes);
		Assert.assertFalse(gerenciador.cabe(novaSessao));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario(){
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessaoDasDez));
	}
	
	@Test
	public void garanteQueNadaDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoJaExistente(){
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		Sessao sessao = new Sessao(sessaoDasDez.getHorario().minusHours(1), rogueOne, sala3D);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessao));
	}
	
	@Test
	public void garanteQueNadaDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente(){
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		Sessao sessao = new Sessao(LocalTime.parse("11:00:00"), rogueOne, sala3D);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessao));
	}

	@Test
	public void garanteQueDevePermitirUmaInsercaoEntreDoisFilmes(){
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez, sessaoDasDezoito);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertTrue(gerenciador.cabe(sessaoDasTreze));
	}
}









