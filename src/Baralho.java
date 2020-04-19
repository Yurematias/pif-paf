package jogodecartas;

import java.util.Random;

public class Baralho {

    private final Carta[] CARTAS;
    private final Random ALEATORIO;
    private int contador;
    // indica a carta de cima do baralho para ser puxada 
    private int lastCard = 51;

    // retorna última carta do baralho 
    public Carta puxarDoBaralho() {
        return CARTAS[lastCard--];
    }

    public int getLastCard() {
        return this.lastCard;
    }

    public Baralho() {
        ALEATORIO = new Random();
        CARTAS = new Carta[52];
        // 1 ---> A, 11 ---> J, 12 ---> Q, 13 ---> K
        int[] face = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        String[] naipe = {"copas", "espadas", "ouros", "paus"};

        int cont = 0;
        for (String naipe1 : naipe) {
            for (int j = 0; j < face.length; j++) {
                CARTAS[cont++] = new Carta(face[j], naipe1);
            }
        }
    }

    public void embaralhar() {
        System.out.println("\t\tEMBARALHANDO\n");
        int num, num2;
        Carta temp;
        for (Carta CARTAS1 : CARTAS) {
            num = ALEATORIO.nextInt(CARTAS.length);
            num2 = ALEATORIO.nextInt(CARTAS.length);
            temp = CARTAS[num];
            CARTAS[num] = CARTAS[num2];
            CARTAS[num2] = temp;
        }
    }

    public Carta[] distribuirCartas(int qtdCartas) {
        System.out.println("------------DISTRIBUINDO BARALHO-----------------");

        Carta[] cartasJogador = new Carta[qtdCartas];

        for (int i = 0; i < qtdCartas; i++) {
            cartasJogador[i] = CARTAS[contador];
            contador++;
        }

        return cartasJogador;
    }

    public void mostrarBaralho() {
        System.out.println("------------APRESENTANDO BARALHO-----------------");
        for (Carta carta : CARTAS) {
            System.out.println(carta.toString());
        }
    }
}