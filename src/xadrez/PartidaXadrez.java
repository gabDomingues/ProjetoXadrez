package xadrez;


import tabuleiro.Peca;
import tabuleiro.Posicao;
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
	
	public PecaXadrez performChessMove(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosition();
		Posicao destino = posicaoDestino.toPosition();
		
		validarPosicaoOrigem(origem);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		return (PecaXadrez) pecaCapturada;
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if(!tabuleiro.thereIsAPiece(posicao)) {
			throw new ChessException("Posicao nao esta no tabuleiro!");
		}
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		return pecaCapturada;
	}
	
	private void placeNewPiece(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosition());
	}
	
	private void setupInicial() {
		
		placeNewPiece('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('c', 2,new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('d', 1, new Rei(tabuleiro, Cor.BRANCO));
        placeNewPiece('c', 7, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('c', 8, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('d', 7, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('e', 7, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('e', 8, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('d', 8, new Rei(tabuleiro, Cor.PRETO));
		}
}
