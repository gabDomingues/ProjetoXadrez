package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "P";
	}
	
	@Override
	public boolean[][] movimentosPossivel() {
		
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);
		
		if(getCor()== Cor.BRANCO) {
			
			p.setvalues(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setvalues(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p1 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p) 
					&& getTabuleiro().positionExists(p1) && !getTabuleiro().thereIsAPiece(p1)
					&& getContMov() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setvalues(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if(getTabuleiro().positionExists(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setvalues(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if(getTabuleiro().positionExists(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		else {
			p.setvalues(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setvalues(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p1 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p) 
					&& getTabuleiro().positionExists(p1) && !getTabuleiro().thereIsAPiece(p1)
					&& getContMov() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setvalues(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if(getTabuleiro().positionExists(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setvalues(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if(getTabuleiro().positionExists(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		
		return mat;
	}

	
}
