package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{

	private PartidaXadrez partida;
	
	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partida) {
		super(tabuleiro, cor);
		this.partida = partida;
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
			
			//en passant branco
			if(posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().positionExists(esquerda) && existePecaAdversaria(esquerda) &&
						getTabuleiro().peca(esquerda) == partida.getVuneravel()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().positionExists(direita) && existePecaAdversaria(direita) &&
						getTabuleiro().peca(direita) == partida.getVuneravel()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}
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
			
			//en passant preto
			if(posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().positionExists(esquerda) && existePecaAdversaria(esquerda) &&
						getTabuleiro().peca(esquerda) == partida.getVuneravel()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().positionExists(direita) && existePecaAdversaria(direita) &&
						getTabuleiro().peca(direita) == partida.getVuneravel()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
		}
		
		return mat;
	}

	
}
