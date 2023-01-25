package xadrez;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	
	private Tabuleiro tabuleiro;
	
	private boolean check;
	private boolean checkMate;
	
	private List<Peca> pecasTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	
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
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
		
		if(testCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ChessException("Voce nao pode se colocar em check");
		}
		
		check = (testCheck(oponente(jogadorAtual))) ? true : false;
		if( testCheckMate(oponente(jogadorAtual))) {
			checkMate = true; 
		} else {
			nextTurn();			
		}
		
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
		
		if(pecaCapturada != null) {
			pecasTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca p = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, origem);
		
		if(pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasTabuleiro.add(pecaCapturada);
		}
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		
		for( Peca p : list) {
			if( p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Nao existe Rei" + cor + " no tabuleiro");
	}
	
	private boolean testCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosition();
		List<Peca> pecasOponente = pecasTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		
		for(Peca p : pecasOponente) {
			boolean[][] mat = p.movimentosPossivel();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Cor cor) {
		if(!testCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		
		for(Peca p : list) {
			boolean[][] mat = p.movimentosPossivel();
			for(int i = 0; i < tabuleiro.getLinhas(); i++) {
				for(int j = 0; j < tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().toPosition();
						Posicao destino = new Posicao(i,j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testCheck = testCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void placeNewPiece(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosition());
		pecasTabuleiro.add(peca);
	}
	
	private void setupInicial() {
		
		placeNewPiece('h', 7, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('d', 1,new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('e', 1, new Rei(tabuleiro, Cor.BRANCO));
        
        placeNewPiece('b', 8, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('a', 8, new Rei(tabuleiro, Cor.PRETO));
		}
	
	private void nextTurn() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO ) ? Cor.PRETO : Cor.BRANCO;
	}
	
	
}
