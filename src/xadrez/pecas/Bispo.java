package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez{

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	
	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] movimentosPossivel() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//noroeste
		p.setvalues(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setvalues(p.getLinha() - 1, p.getColuna() - 1);
		}
		if(getTabuleiro().positionExists(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//nordeste
		p.setvalues(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setvalues(p.getLinha() - 1, p.getColuna() + 1);
		}
		if(getTabuleiro().positionExists(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudeste
		p.setvalues(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setvalues(p.getLinha() + 1, p.getColuna() + 1);
		}
		if(getTabuleiro().positionExists(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//sudoeste
		p.setvalues(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setvalues(p.getLinha() + 1, p.getColuna() - 1);
		}
		if(getTabuleiro().positionExists(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}
