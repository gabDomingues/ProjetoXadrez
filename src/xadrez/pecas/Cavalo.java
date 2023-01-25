package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez{

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	@Override
	public String toString() {
		return "C";
	}

	@Override
	public boolean[][] movimentosPossivel() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//
		p.setvalues(posicao.getLinha() - 1, posicao.getColuna() - 2);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//
		p.setvalues(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		//
		p.setvalues(posicao.getLinha() + 1, posicao.getColuna() - 2);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
				
		//
		p.setvalues(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
	
		//
		p.setvalues(posicao.getLinha() - 2, posicao.getColuna() - 1);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
			
		//
		p.setvalues(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}	
		
		//
		p.setvalues(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//
		p.setvalues(posicao.getLinha() + 2, posicao.getColuna() + 1 );
		if( getTabuleiro().positionExists(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}
