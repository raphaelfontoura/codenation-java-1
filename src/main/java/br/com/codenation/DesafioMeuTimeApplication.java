package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;

public class DesafioMeuTimeApplication implements MeuTimeInterface {
	
	public Map<Long, Time> listaTime = new HashMap<>();
	public Map<Long, Jogador> listaJogador = new HashMap<>();	
	

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		
		if (listaTime.containsKey(id)) {
			throw new IdentificadorUtilizadoException();	
		}
		
		listaTime.put(id, new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		
		if (listaJogador.containsKey(id)) {
			throw new IdentificadorUtilizadoException();
		}
		buscarTime(idTime).addJogador(id);
		
		listaJogador.put(id, new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));		
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		listaTime.get(buscarJogador(idJogador).getIdTime()).setIdCapitao(idJogador);
	}
	

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {				
		
		if (buscarTime(idTime).getIdCapitao() == null) {
			throw new CapitaoNaoInformadoException();				
		}
		return buscarTime(idTime).getIdCapitao();
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		return buscarJogador(idJogador).getNome();
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {		
		return buscarTime(idTime).getNome();
	}
	
	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {		
		return buscarTime(idTime).getJogadores();		
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		
		//List<Long> jogadores = new ArrayList<>();
		List<Long> jogadores = buscarJogadoresDoTime(idTime);
		Jogador melhorJogador = null;
		
		for (Long idJogador : jogadores) {
			Jogador jogador = buscarJogador(idJogador);
			if (melhorJogador == null) {
				melhorJogador = jogador;
			} else {
				if (melhorJogador.getNivelHabilidade() < jogador.getNivelHabilidade()){
					melhorJogador = jogador;
				}
			}
		}

		return melhorJogador.getId();
		
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		
		List<Long> jogadores = buscarJogadoresDoTime(idTime);
		
		Jogador jogadorMaisVelho =null;
		
		for (Long idJogador : jogadores) {
			Jogador jogador = buscarJogador(idJogador);
			if (jogadorMaisVelho == null) {
				jogadorMaisVelho = jogador;
			} else {
				if (jogadorMaisVelho.getDataNascimento().isAfter(jogador.getDataNascimento())){
					jogadorMaisVelho = jogador;
				}
			}
		}

		return jogadorMaisVelho.getId();
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		if(listaTime.size()==0) return new ArrayList<Long>();
		return new ArrayList<Long>(listaTime.keySet());
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		
		List<Long> jogadores = buscarJogadoresDoTime(idTime);
		Jogador jogadorComMaiorSalario = null;
		
		for(Long idJogador : jogadores) {
			Jogador jogador = buscarJogador(idJogador);
			if (jogadorComMaiorSalario == null) {
				jogadorComMaiorSalario = jogador;
			}else if(jogadorComMaiorSalario.getSalario().compareTo(jogador.getSalario()) == -1) {
				    jogadorComMaiorSalario = jogador;
			} 
		}
		return jogadorComMaiorSalario.getId();
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		return buscarJogador(idJogador).getSalario();
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		
		if (listaJogador.size() == 0) {
			return new ArrayList<Long>();
		}
		
		List<Jogador> jogadoresTop = new ArrayList<Jogador>(listaJogador.values());	
		Comparator<Jogador> compareNivelHabilidade = (Jogador j1, Jogador j2) -> j1.getNivelHabilidade().compareTo(j2.getNivelHabilidade());
		
		Collections.sort(jogadoresTop, compareNivelHabilidade.reversed());
		
		List<Long> idJogadoresTop = new ArrayList<>();
		for(int a = 0 ; a < top ; a++) {
			idJogadoresTop.add(jogadoresTop.get(a).getId());
		}
		return idJogadoresTop;
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {		
		
		Time timeCasa = buscarTime(timeDaCasa);
		Time timeFora = buscarTime(timeDeFora);		
		
		if(timeCasa.getCorUniformePrincipal() == timeFora.getCorUniformePrincipal()) {
			return timeFora.getCorUniformeSecundario();
		}
		
		return timeFora.getCorUniformePrincipal();
		
	}

	private Time buscarTime(Long idTime) {
		if (!listaTime.containsKey(idTime)) {
			throw new TimeNaoEncontradoException();
		}
		return listaTime.get(idTime);
	}

	private Jogador buscarJogador(Long idJogador) {
		if (!listaJogador.containsKey(idJogador)) {
			throw new JogadorNaoEncontradoException();		
		}
		return listaJogador.get(idJogador);
	}
	
	


}
