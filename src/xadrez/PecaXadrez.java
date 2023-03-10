package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca{
	
	private Cor cor;
	
	private int contMov;
	
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public void increaseContMov() {
		contMov++;
	}
	
	public void decreaseContMov() {
		contMov--;
	}
	
	public int getContMov() {
		return contMov;
	}
	
	protected boolean existePecaAdversaria(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.fromPosition(posicao);
	}
}
