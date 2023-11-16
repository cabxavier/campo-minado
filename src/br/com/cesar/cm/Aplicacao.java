package br.com.cesar.cm;

import br.com.cesar.cm.modelo.Tabuleiro;
import br.com.cesar.cm.visao.TabuleiroConsole;

public class Aplicacao {

	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 3);		
		new TabuleiroConsole(tabuleiro);		
	}
}
