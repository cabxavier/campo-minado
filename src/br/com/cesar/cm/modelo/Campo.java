package br.com.cesar.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cesar.cm.excecao.ExplosaoException;

public class Campo {

	private final int linha;
	private final int coluna;

	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;

	private List<Campo> vizinhos = new ArrayList<>();

	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = this.linha != vizinho.linha;
		boolean colunaDiferente = this.coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;

		if (deltaGeral == 1 && !diagonal) {
			this.vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			this.vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	void alternarMarcacao() {
		if (!this.aberto) {
			this.marcado = !this.marcado;
		}
	}

	boolean abrir() {

		if (!this.aberto && !this.marcado) {
			this.aberto = true;

			if (this.minado) {
				throw new ExplosaoException();
			}

			if (this.vizinhaSegura()) {
				this.vizinhos.forEach(v -> v.abrir());
			}

			return true;

		} else {

			return false;
		}
	}

	boolean vizinhaSegura() {
		return this.vizinhos.stream().noneMatch(v -> v.minado);
	}

	void minar() {
		this.minado = true;
	}

	public boolean isMarcado() {
		return this.marcado;
	}

	public boolean isAberto() {
		return this.aberto;
	}

	public boolean isFechado() {
		return !this.isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !this.minado && this.aberto;
		boolean protegido = this.minado && this.marcado;

		return desvendado || protegido;
	}

	long minasNaVizinha() {
		return this.vizinhos.stream().filter(v -> v.minado).count();
	}

	void reiniciar() {
		this.aberto = false;
		this.minado = false;
		this.marcado = false;
	}

	public String toString() {
		if (this.marcado) {
			return "x";
		} else if (this.aberto && this.minado) {
			return "*";
		} else if (this.aberto && this.minasNaVizinha() > 0) {
			return Long.toString(minasNaVizinha());
		} else if (this.aberto) {
			return "";
		} else {
			return "?";
		}
	}
}
