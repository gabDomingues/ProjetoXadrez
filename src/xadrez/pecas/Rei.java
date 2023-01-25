package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	private PartidaXadrez partida;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	private boolean testeRoque(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContMov() == 0;
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] movimentosPossivel() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// acima
		p.setvalues(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// abaixo
		p.setvalues(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// esquerda
		p.setvalues(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// direita
		p.setvalues(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// noroeste (esquerda cima)
		p.setvalues(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nordeste (direita cima)
		p.setvalues(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudoeste (esquerda baixo)
		p.setvalues(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudeste (direita baixo)
		p.setvalues(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Roque
		if (getContMov() == 0 && !partida.getCheck()) {
			// roque pequeno
			Posicao posTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);

			if (testeRoque(posTorre1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);

				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			
			
			// roque grande
			Posicao posTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);

			if (testeRoque(posTorre2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);

				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null 
						&& getTabuleiro().peca(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}
		}

		return mat;
	}
}
