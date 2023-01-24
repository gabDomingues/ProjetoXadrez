package tabuleiro;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas < 1 || colunas < 1) {
			throw new BoardException("Erro ao criar tabuleiro: Pelo menos 1 linha e 1 coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		this.pecas = new Peca[this.linhas][this.colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int linha, int coluna) {
		if(!positionExists(linha, coluna) ) {
			throw new BoardException("Posicao nao existe");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicao posicao) {
		if(!positionExists(posicao)) {
			throw new BoardException("Posicao nao existe");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocarPeca(Peca peca, Posicao posicao) {
		
		if(thereIsAPiece(posicao)) {
			throw new BoardException("Posicao ja ocupada nessa posicao " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removerPeca(Posicao posicao) {
		if(!positionExists(posicao)) {
			throw new BoardException("Posicao nao existe");
		}
		if(peca(posicao) == null) {
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	private boolean positionExists(int linha, int coluna) {
		return linha>= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean positionExists(Posicao posicao) {
		return positionExists(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean thereIsAPiece(Posicao posicao) {
		if(!positionExists(posicao)) {
			throw new BoardException("Posicao nao existe");
		}
		return peca(posicao) != null;
	}
}
