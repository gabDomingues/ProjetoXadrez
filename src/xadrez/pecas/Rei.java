package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] movimentosPossivel() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//acima
		p.setvalues(posicao.getLinha() - 1, posicao.getColuna());
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//abaixo
		p.setvalues(posicao.getLinha() + 1, posicao.getColuna());
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
				
		//esquerda
		p.setvalues(posicao.getLinha(), posicao.getColuna() - 1);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
				
		//direita
		p.setvalues(posicao.getLinha(), posicao.getColuna() + 1);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//noroeste (esquerda cima)
		p.setvalues(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
			
		//nordeste (direita cima)
		p.setvalues(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}	
		
		//sudoeste (esquerda baixo)
		p.setvalues(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudeste (direita baixo)
		p.setvalues(posicao.getLinha() + 1, posicao.getColuna() + 1 );
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}
