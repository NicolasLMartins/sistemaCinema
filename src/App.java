import java.io.File;
import java.util.Scanner;

public class App {
    public static Scanner input = new Scanner(System.in);

    public static void carregarDados(char[][] mapaAssentos, char[][] sexoOcupantes, int[][] idadeOcupantes,
            double[][] valorOcupantes, double valorIngresso) {
        System.out.print("\033\143");

        System.out.println("Digite o nome do arquivo: ");
        String nomeArquivo = input.nextLine();

        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            System.out.println("Arquivo nao encontrado!");
            return;
        }

        try {
            Scanner leitor = new Scanner(arquivo);
            if (leitor.hasNextLine()) {
                leitor.nextLine(); // Pula cabeçalho
            }

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                String[] partes = linha.split(",");

                if (partes.length == 3) {
                    String idAssento = partes[0];
                    char sexo = partes[1].charAt(0);
                    int idade = Integer.parseInt(partes[2]);

                    int linhaIndex = idAssento.charAt(0) - 'A';
                    int colunaIndex = Integer.parseInt(idAssento.substring(1)) - 1;

                    mapaAssentos[linhaIndex][colunaIndex] = 'X';
                    sexoOcupantes[linhaIndex][colunaIndex] = sexo;
                    idadeOcupantes[linhaIndex][colunaIndex] = idade;
                    valorOcupantes[linhaIndex][colunaIndex] = (idade >= 0 && idade <= 17) || idade >= 60
                            ? valorIngresso / 2
                            : valorIngresso;
                }
            }

            leitor.close();
            System.out.println("Dados carregados!");

        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo!");
        }

        System.out.println("Pressione ENTER para continuar...");
        input.nextLine();
    }

    public static void consultarAssento(char[][] mapaAssentos, char[][] sexoOcupantes, int[][] idadeOcupantes,
            double[][] valorOcupantes, double valorIngresso) {
        System.out.print("\033\143");

        System.out.println("===== CONSULTAR SITUAÇÃO DE UM ASSENTO =====");
        System.out.print("Informe o assento (ex: A1): ");
        String assento = input.nextLine().toUpperCase();

        if (assento.length() < 2) {
            System.out.println("Formato inválido!");
            System.out.println("Pressione ENTER para continuar...");
            input.nextLine();
            return;
        }

        char letra = assento.charAt(0);
        int linha = letra - 'A';

        int coluna;
        try {
            coluna = Integer.parseInt(assento.substring(1)) - 1;
        } catch (Exception e) {
            System.out.println("Número do assento inválido!");
            System.out.println("Pressione ENTER para continuar...");
            input.nextLine();
            return;
        }

        if (linha < 0 || linha >= mapaAssentos.length || coluna < 0 || coluna >= mapaAssentos[0].length) {
            System.out.println("Assento fora dos limites!");
            System.out.println("Pressione ENTER para continuar...");
            input.nextLine();
            return;
        }

        if (mapaAssentos[linha][coluna] == '.') {
            System.out.println("O assento " + assento + " está LIVRE.");
        } else {
            System.out.println("O assento " + assento + " está RESERVADO.");
            char sexo = sexoOcupantes[linha][coluna];
            int idade = idadeOcupantes[linha][coluna];

            System.out.println("Sexo do ocupante: " + sexo);
            System.out.println("Idade do ocupante: " + idade);
            System.out.printf("Valor pago: R$ %.2f\n", valorOcupantes[linha][coluna]);
        }

        System.out.println("Pressione ENTER para continuar...");
        input.nextLine();
    }

    public static void reservarAssentos(char[][] mapaAssentos, char[][] sexoOcupantes, int[][] idadeOcupantes,
            double[][] valorOcupantes, double valorIngresso) {
        System.out.print("\033\143");

        System.out.println("===== RESERVA DE N ASSENTOS =====");
        System.out.print("Informe o assento inicial (ex: C4): ");
        String assento = input.nextLine().toUpperCase();

        if (assento.length() < 2) {
            System.out.println("Formato inválido!");
            System.out.println("Pressione ENTER para continuar...");
            input.nextLine();
            return;
        }

        char letra = assento.charAt(0);
        int linha = letra - 'A';

        int coluna;
        try {
            coluna = Integer.parseInt(assento.substring(1)) - 1;
        } catch (Exception e) {
            System.out.println("Número do assento inválido!");
            System.out.println("Pressione ENTER para continuar...");
            input.nextLine();
            return;
        }

        if (linha < 0 || linha >= mapaAssentos.length || coluna < 0 || coluna >= mapaAssentos[0].length) {
            System.out.println("Assento fora dos limites!");
            System.out.println("Pressione ENTER para continuar...");
            input.nextLine();
            return;
        }

        System.out.print("Informe a quantidade de assentos a reservar: ");
        int quantidade = input.nextInt();
        input.nextLine();

        if (coluna + quantidade > mapaAssentos[0].length) {
            System.out.println("Não é possível liberar essa quantidade de assentos a partir do assento informado.");
            System.out.println("Pressione ENTER para continuar...");
            input.nextLine();
            return;
        }

        int reservados = 0;

        for (int i = coluna; i < coluna + quantidade; i++) {
            if (mapaAssentos[linha][i] == 'X') {
                System.out.println("Sobreposição detectada, tente novamente com outros assentos...");
                System.out.println("Pressione ENTER para continuar...");
                input.nextLine();
                return;
            }
        }

        double valor = 0;

        for (int j = coluna; j < coluna + quantidade; j++) {
            System.out.print("\033\143");

            System.out.println("===== RESERVA DE N ASSENTOS =====");

            System.out.println("Qual o sexo da pessoa no assento " + letra + (j + 1) + " (F/M)?");
            char sexo = input.next().charAt(0);

            if (sexo != 'F' && sexo != 'M') {
                System.out.println("Entrada inválida.");
                System.out.println("Pressione ENTER para continuar...");
                input.nextLine(); // Limpa buffer
                input.nextLine();
                return;
            }

            System.out.println("Qual a idade da pessoa no assento " + letra + (j + 1) + "?");

            int idade = input.nextInt();

            mapaAssentos[linha][j] = 'X';
            sexoOcupantes[linha][j] = sexo;
            idadeOcupantes[linha][j] = idade;
            valorOcupantes[linha][j] = (idade >= 0 && idade <= 17) || idade >= 60 ? valorIngresso / 2 : valorIngresso;
            valor += valorOcupantes[linha][j];
            reservados++;
        }

        System.out.println("Total de assentos reservados: " + reservados);
        System.out.println("Total a pagar: R$ " + String.format("%.2f", valor));
        System.out.println("Pressione ENTER para continuar...");
        input.nextLine(); // Limpa buffer
        input.nextLine();
    }

    public static void liberarAssentos(char[][] mapaAssentos, char[][] sexoOcupantes, int[][] idadeOcupantes,
            double[][] valorOcupantes) {
        System.out.print("\033\143");

        System.out.println("===== LIBERAR RESERVA DE N ASSENTOS =====");
        System.out.print("Informe o assento inicial (ex: B3): ");
        String assento = input.nextLine().toUpperCase();

        if (assento.length() < 2) {
            System.out.println("Formato inválido!");
            System.out.println("Pressione ENTER para continuar...");
            input.nextLine();
            return;
        }

        char letra = assento.charAt(0);
        int linha = letra - 'A';

        int coluna;
        try {
            coluna = Integer.parseInt(assento.substring(1)) - 1;
        } catch (Exception e) {
            System.out.println("Número do assento inválido!");
            System.out.println("Pressione ENTER para continuar...");
            input.nextLine();
            return;
        }

        if (linha < 0 || linha >= mapaAssentos.length || coluna < 0 || coluna >= mapaAssentos[0].length) {
            System.out.println("Assento fora dos limites!");
            System.out.println("Pressione ENTER para continuar...");
            input.nextLine();
            return;
        }

        System.out.print("Informe a quantidade de assentos a liberar: ");
        int quantidade = input.nextInt();
        input.nextLine();

        if (coluna + quantidade > mapaAssentos[0].length) {
            System.out.println("Não é possível liberar essa quantidade de assentos a partir do assento informado.");
            System.out.println("Pressione ENTER para continuar...");
            input.nextLine();
            return;
        }

        int liberados = 0;

        for (int j = coluna; j < coluna + quantidade; j++) {
            if (mapaAssentos[linha][j] == 'X') {
                mapaAssentos[linha][j] = '.';
                sexoOcupantes[linha][j] = ' ';
                idadeOcupantes[linha][j] = 0;
                valorOcupantes[linha][j] = 0.0d;
                liberados++;
            }
        }

        System.out.println("Total de assentos liberados: " + liberados);
        System.out.println("Pressione ENTER para continuar...");
        input.nextLine();
    }

    public static void visualizarMapa(char[][] mapaAssentos) {
        System.out.print("\033\143");

        char currentLine;
        int asciiLetter = 65;

        System.out.println("====== MAPA DO CINEMA ======");
        for (int i = 0; i < mapaAssentos.length + 1; i++) {
            currentLine = i > 0 ? (char) asciiLetter++ : ' ';
            System.out.print(currentLine);

            for (int j = 0; j < mapaAssentos[0].length; j++) {
                if (i == 0) {
                    System.out.printf(" %02d", j + 1);
                    continue;
                }

                System.out.print(" " + mapaAssentos[i - 1][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Pressione ENTER para voltar ao menu...");
        input.nextLine();
    }

    public static void main(String[] args) {
        System.out.print("\033\143");
        double valorIngresso = 0;
        int fileiras = 0;
        int assentos = 0;
        int opcao;

        System.out.println("Informe o valor do ingresso: ");
        valorIngresso = input.nextDouble();

        System.out.println("Informe a quantidade de fileiras: ");
        fileiras = input.nextInt();

        System.out.println("Informe a quantidade de assentos por fileira: ");
        assentos = input.nextInt();

        char[][] mapaAssentos = new char[fileiras][assentos];
        char[][] sexoOcupantes = new char[fileiras][assentos];
        int[][] idadeOcupantes = new int[fileiras][assentos];
        double[][] valorOcupantes = new double[fileiras][assentos];

        for (int i = 0; i < fileiras; i++) {
            for (int j = 0; j < assentos; j++) {
                mapaAssentos[i][j] = '.';
                sexoOcupantes[i][j] = ' ';
                idadeOcupantes[i][j] = 0;
                valorOcupantes[i][j] = 0.0d;
            }
        }

        do {
            System.out.print("\033\143");

            System.out.println("====== MENU PRINCIPAL ======");
            System.out.println("1. Carregar dados");
            System.out.println("2. Consultar situação de um assento");
            System.out.println("3. Fazer reservas de n assentos");
            System.out.println("4. Liberar reserva de n assentos");
            System.out.println("5. Visualizar mapa do cinema");
            System.out.println("6. Relatórios");
            System.out.println("7. Salvar dados");
            System.out.println("8. Integrantes");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    carregarDados(mapaAssentos, sexoOcupantes, idadeOcupantes, valorOcupantes, valorIngresso);

                    break;
                case 2:
                    consultarAssento(mapaAssentos, sexoOcupantes, idadeOcupantes, valorOcupantes, valorIngresso);
                    break;
                case 3:
                    reservarAssentos(mapaAssentos, sexoOcupantes, idadeOcupantes, valorOcupantes, valorIngresso);
                    break;
                case 4:
                    liberarAssentos(mapaAssentos, sexoOcupantes, idadeOcupantes, valorOcupantes);

                    break;
                case 5:
                    visualizarMapa(mapaAssentos);
                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

                    break;
                case 9:

                    break;

                default:
                    break;
            }
        } while (opcao != 9);

    }
}