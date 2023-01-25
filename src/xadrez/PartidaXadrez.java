package xadrez;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Dama;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	
	private Tabuleiro tabuleiro;
	
	private boolean check;
	private boolean checkMate;
	private PecaXadrez vuneravel;
	private PecaXadrez promovido;
	
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
	
	public PecaXadrez getPromovido() {
		return promovido;
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
	
	public PecaXadrez getVuneravel() {
		return vuneravel;
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
		
		PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(destino); 
		
		//promocao
		promovido = null;
		if(pecaMovida instanceof Peao) {
			if( pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0 || 
					pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7 ) {
				promovido = (PecaXadrez)tabuleiro.peca(destino);
				promovido = trocarPecaPromovida("D");
			}
		}
		
		check = (testCheck(oponente(jogadorAtual))) ? true : false;
		if( testCheckMate(oponente(jogadorAtual))) {
			checkMate = true; 
		} else {
			nextTurn();			
		}
		
		//en Passant
		if(pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 ||
				pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() + 2))) {
			vuneravel = pecaMovida;
		}
		else {
			vuneravel = null;
		}
		
		return (PecaXadrez) pecaCapturada;
	}
	
	public PecaXadrez trocarPecaPromovida(String tipo) {
		if(promovido == null) {
			throw new IllegalStateException("Nao tem peca a ser promovida!");
		}
		if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("D")) {
			return promovido;
		}
		
		Posicao pos = promovido.getPosicaoXadrez().toPosition(); 
		Peca p = tabuleiro.removerPeca(pos);
		pecasTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(tipo, promovido.getCor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecasTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaXadrez novaPeca(String tipo, Cor cor) {
		if( tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if( tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if( tipo.equals("D")) return new Dama(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
		
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
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
		p.increaseContMov();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		if(pecaCapturada != null) {
			pecasTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		//#Roque pequeno
		if(p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.increaseContMov();
		}
		
		//#Roque grande
		if(p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.increaseContMov();
		}
		
		//#en passant
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao posicaoPeao;
				if(p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				}
				else {
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasTabuleiro.remove(pecaCapturada);
			}
		}
		
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p =(PecaXadrez) tabuleiro.removerPeca(destino);
		p.decreaseContMov();
		tabuleiro.colocarPeca(p, origem);
		
		if(pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasTabuleiro.add(pecaCapturada);
		}
		
		//#Roque pequeno
		if(p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), destino.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), destino.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.decreaseContMov();
		}
		
		//#Roque grande
		if(p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), destino.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), destino.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.decreaseContMov();
		}
		
		//#en passant
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada == vuneravel) {
				PecaXadrez peao = (PecaXadrez)tabuleiro.removerPeca(destino);
				Posicao posicaoPeao;
				if(p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(3, destino.getColuna());
				}
				else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.colocarPeca(peao, posicaoPeao);
				
			
			}
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
		
		placeNewPiece('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('h', 1,new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        placeNewPiece('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        placeNewPiece('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        placeNewPiece('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        placeNewPiece('d', 1, new Dama(tabuleiro, Cor.BRANCO));

        
        placeNewPiece('a', 8, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('h', 8, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        placeNewPiece('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
        placeNewPiece('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
        placeNewPiece('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
        placeNewPiece('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
        placeNewPiece('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
        placeNewPiece('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
        placeNewPiece('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
        placeNewPiece('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
        placeNewPiece('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        placeNewPiece('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        placeNewPiece('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        placeNewPiece('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        placeNewPiece('d', 8, new Dama(tabuleiro, Cor.PRETO));
		}
	
	private void nextTurn() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO ) ? Cor.PRETO : Cor.BRANCO;
	}
	
	
}
