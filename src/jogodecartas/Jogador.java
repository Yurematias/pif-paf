package jogodecartas;

import java.util.Arrays;

public class Jogador {

    private final String NOME;
    private Carta[] cartas;

    public Jogador(String nome) {
        this.NOME = nome;
    }

    // pegar uma carta específica da mão
    public Carta getCarta(int i) {
        return cartas[i - 1];
    }

    // quando o jogador descartar uma carta e pegar outra
    // precisará ocorrer a substituição pela carta pega 
    public void substituirCarta(int i, Carta c) {
        cartas[i - 1] = c;
    }

    public String getNome() {
        return this.NOME;
    }

    public void setCartas(Carta[] cartas) {
        this.cartas = cartas;
    }

    public void mostrarCartas() {
        // ordenar o array das cartas da mão antes de mostrá-lo 
        Arrays.sort(cartas);
        for (int i = 0; i < cartas.length; i++) {
            System.out.println(i + 1 + " - " + cartas[i]);
        }
    }
}