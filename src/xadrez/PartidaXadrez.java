package xadrez;


import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		setupInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
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
	
	public boolean[][] movimentosPossivel(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.toPosition();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossivel();
	}
	
	public PecaXadrez performChessMove(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosition();
		Posicao destino = posicaoDestino.toPosition();
		
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		nextTurn();
		return (PecaXadrez) pecaCapturada;
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if(!tabuleiro.thereIsAPiece(posicao)) {
			throw new ChessException("Posicao nao esta no tabuleiro!");
		}
		if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new ChessException("A pessoa escolhida nao e sua");
		}
		if(!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new ChessException("Nao existe movimentos possiveis para a peca!");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new ChessException("A peca escolhida nao pode mover para o destino!");
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
	
	private void nextTurn() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO ) ? Cor.PRETO : Cor.BRANCO;
	}
}
