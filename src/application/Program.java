package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ChessException;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		PartidaXadrez partida = new PartidaXadrez();
		List<PecaXadrez> capturada = new ArrayList<>();
		
		
		while(!partida.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printPartida(partida, capturada);
				System.out.println();
				System.out.print("Posicao Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partida.movimentosPossivel(origem);
				UI.clearScreen();
				UI.printTabuleiro(partida.getPecas(), movimentosPossiveis);
				
				System.out.println();
				System.out.print("Posicao Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				
				PecaXadrez pecaCapturada = partida.performChessMove(origem, destino);
				if(pecaCapturada != null) {
					capturada.add(pecaCapturada);
				}
				
				
				if(partida.getPromovido() != null) {
					System.out.println("Digite peca para promover (B/C/D/T): ");
					String tipo = sc.nextLine().toUpperCase();
					while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("D")) {
						System.out.println("Invalido!");
						System.out.println("Digite peca para promover (B/C/D/T): ");
						tipo = sc.nextLine().toUpperCase();
					}
					partida.trocarPecaPromovida(tipo);
				}
			}
			catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();

			}
		}
		UI.clearScreen();
		UI.printPartida(partida, capturada);
	}

}
