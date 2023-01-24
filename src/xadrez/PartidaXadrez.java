package xadrez;


import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		setupInicial();
	}
	
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		
		for(int i = 0; i < tabuleiro.getLinhas(); i++) {
			for(int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] =(PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	private void placeNewPiece(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosition());
	}
	
	private void setupInicial() {
		placeNewPiece('b', 6, new Torre(tabuleiro, Cor.BRANCO));
		placeNewPiece('e',8,new Rei(tabuleiro, Cor.PRETO));
		placeNewPiece('e',1,new Rei(tabuleiro, Cor.PRETO));
		}
}
