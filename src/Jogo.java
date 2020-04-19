package jogodecartas;

import java.util.Scanner;

public class Jogo {

    private final Scanner entrada = new Scanner(System.in);
    private final Baralho BARALHO;
    private Jogador[] jogadores;
    private Carta lixo;

    public Jogo() {
        BARALHO = new Baralho();
        BARALHO.mostrarBaralho();
        BARALHO.embaralhar();
        BARALHO.mostrarBaralho();
    }

    public void iniciarJogo() {
        jogadores = new Jogador[2];
        System.out.println();
        for (int i = 0; i < jogadores.length; i++) {
            System.out.print("Jogador " + (i + 1) + ", digite seu nome: ");
            jogadores[i] = new Jogador(entrada.next());
        }
    }

    // distribui as cartas 
    public void distribuirCartas(int qtdCartas) {
        for (Jogador jogadore : jogadores) {
            jogadore.setCartas(BARALHO.distribuirCartas(qtdCartas));
        }
    }

    // mostra as cartas dos jogadores 
    public void mostrarCartas() {
        for (Jogador jogadore : jogadores) {
            System.out.println("-----------CARTAS DE " + jogadore.getNome().toUpperCase() + "------------------");
            jogadore.mostrarCartas();
        }
    }

    private boolean verificarTrinca(Carta a, Carta b, Carta c) {
        boolean contador = false;
        if (a.getFace() == b.getFace() && a.getFace() == c.getFace()) {
            System.out.println("\nVocê formou uma trinca com as cartas: ");
            System.out.println(a + "\n" + b + "\n" + c);
            contador = true;
        }
        return contador;
    }

    // verifica se é sequencia, retorna true se for, false se não for 
    private boolean verificarSequencia(Carta a, Carta b, Carta c) {
        boolean seq = false;
        if (b.getFace() == (a.getFace() + 1) && c.getFace() == (a.getFace() + 2) && naipesDiferentes(a, b, c)) {
            System.out.println("\nVocê formou uma sequência com as cartas: ");
            System.out.println(a + "\n" + b + "\n" + c);
            seq = true;
        }
        return seq;
    }

    // verifica se as cartas são de naipes diferentes, retorna true se forem, false se não forem  
    private boolean naipesDiferentes(Carta a, Carta b, Carta c) {
        return (a.getNaipe() != b.getNaipe() && b.getNaipe() != c.getNaipe());
    }

    // recebe a vez e diz se o jogador da vez ganhou ou nao 
    private boolean verificarVitoria(int vez) {
        int contador = 0;
        boolean fim = false;
        Carta a, b, c;
        do {
            System.out.println("\nSelecione a " + (contador + 1) + "ª sequência/trinca em ordem: ");
            a = jogadores[vez].getCarta(entrada.nextInt()); // carta 1 da possível combinação 
            b = jogadores[vez].getCarta(entrada.nextInt()); // carta 2 da possível combinação
            c = jogadores[vez].getCarta(entrada.nextInt()); // carta 3 da possível combinação
            if (contador < 3) {
                if (verificarTrinca(a, b, c)) { 
                    contador++;
                } else if (verificarSequencia(a, b, c)) {
                    contador++;
                } else { // se a combinação inserida não for trinca nem sequencia, foi falsa batida
                    System.out.println("Falsa batida, você perdeu o jogo!!"); 
                    fim = true;
                }
            } else if (contador == 3) {
                fim = true;
            }
        } while (fim == false && contador < 3); // executa até que algúem ganhe ou faça 3 combinações 
        return fim;
    }

    // recebe a vez e disponibiliza as opções para o jogador atual puxar a carta, 
    // ao final retorna a carta puxada
    private Carta escolherPuxar(int vez) {
        Carta cartaPuxada = null;
        do {
            System.out.println("\nDe onde pretende puxar? ");
            System.out.println("1 - Puxar do baralho");
            System.out.println("2 - Puxar do lixo");
            System.out.print("Digite sua opção: ");
            System.out.println();
            switch (entrada.nextInt()) {
                case 1: // puxar do baralho 
                    if (BARALHO.getLastCard() > 0) { // verificar se tem cartas no baralho 
                        cartaPuxada = BARALHO.puxarDoBaralho(); // cartaPuxada recebe do baralho 
                        System.out.println(">>> " + jogadores[vez].getNome() + " puxou um " + cartaPuxada + " <<<");
                    } else {
                        System.out.println("* Não há mais cartas no baralho *");
                    }
                    break;
                case 2: // puxar do lixo 
                    if (lixo != null) { // não dá pra puxar do lixo sem cartas no lixo 
                        cartaPuxada = lixo; 
                        System.out.println(">>> " + jogadores[vez].getNome() + " puxou um " + cartaPuxada + " do lixo <<<");
                    } else {
                        System.out.println("* Não há cartas para puxar do lixo *");
                    }
                    break;
                default:
                    System.out.println("* Opção inválida *");
            }
            if (cartaPuxada == null) {
                System.out.print("\nDigite novamente: ");
            }
            // se a carta puxada for nula, ou seja se a execução tiver caído em um dos 2 elses anteriores 
            // o jogador precisará na próxima rodada inserir uma jogada válida
        } while (cartaPuxada == null);
        // retornar a carta puxada 
        return cartaPuxada;
    }

    // substitui a carta escolhida para o descarte com a carta que o jogador puxou anteriormente 
    private void escolherDescarte(int vez, Carta cartaPuxada) {
        int indexToRemove; // representa índice da carta que será removida 
        do {
            System.out.print("\nInsira o número da carta a ser descartada (para a carta puxada digite 10): ");
            indexToRemove = entrada.nextInt();
            if (indexToRemove > 0 && indexToRemove < 10) { // verificar se precisará ocorrer substituição
                lixo = jogadores[vez].getCarta(indexToRemove); // lixo recebe a carta escolhida para descarte 
                System.out.println("\n" + jogadores[vez].getCarta(indexToRemove) + " - Descartado\n");
                jogadores[vez].substituirCarta(indexToRemove, cartaPuxada); // substitui carta descartada com a puxada 
            } else if (indexToRemove == 10) { // verificar se carta descartada será a puxada 
                System.out.println("\nCarta puxada descartada\n");
                lixo = cartaPuxada; 
            } else { 
                System.out.println("\nPosição da carta incorreta.");
            }
        } while (indexToRemove > 10 || indexToRemove < 1);
    }

    // executa o jogo 
    public void executar() {
        boolean fim = false;
        // determina de quem é a vez 
        // se for 0 significa que é vez do jog 1, se for 1 significa que é a vez do jogador 2 
        int vez = 0;
        Carta cartaPuxada;

        do {
            System.out.println("\n-------------- Vez de " + jogadores[vez].getNome() + "--------------");
            System.out.println("\nCartas de " + jogadores[vez].getNome());
            jogadores[vez].mostrarCartas();

            if (lixo != null) {
                System.out.println("\nUltima carta do lixo: " + lixo);
            }

            // faz o jogador puxar a carta que ele selecionar 
            cartaPuxada = escolherPuxar(vez);
            // faz jogador escolher a carta a ser descartada para ao fim substituir a carta descartada 
            // com a puxada anteriormente, a descartada será o novo valor do atributo lixo 
            escolherDescarte(vez, cartaPuxada);
            
            jogadores[vez].mostrarCartas();
            System.out.println("\nVocê deseja bater ?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");
            System.out.print(">> ");

            if (entrada.nextInt() == 1) {
                // a variável controladora do laço recebe o resultado boleano se alguem ganhou
                fim = verificarVitoria(vez);
            }
            vez = vez == 0 ? 1 : 0;    // passar a vez 
        } while (!fim);
    }
}
