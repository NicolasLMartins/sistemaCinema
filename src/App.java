import java.io.File;
import java.util.Scanner;

public class App {
    public static Scanner input = new Scanner(System.in);

    public static void carregarDados(char[][] mapaAssentos, char[][] sexoOcupantes, int[][] idadeOcupantes) {
        System.out.print("\033\143");
        
        System.out.println("Digite o nome do arquivo: ");
        String nomeArquivo = input.nextLine();

        File arquivo = new File(nomeArquivo);

        if(!arquivo.exists()){
            System.out.println("Arquivo nao encontrado!");
            return;
        }

        try {
            Scanner leitor = new Scanner(arquivo);
            if (leitor.hasNextLine()){
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
    public static void main(String[] args) {

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

        // Inicializa todos assentos como livres
        for (int i = 0; i < fileiras; i++){
            for (int j = 0; j < assentos; j++){
                mapaAssentos[i][j] = '.';
                sexoOcupantes[i][j] = ' ';
                idadeOcupantes[i][j] = 0;
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
                    carregarDados(mapaAssentos, sexoOcupantes, idadeOcupantes);
                    
                    break;
                case 2:
                    
                    break;
                case 3:
                    
                    break;
                case 4:
                    
                    break;
                case 5:
                    
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