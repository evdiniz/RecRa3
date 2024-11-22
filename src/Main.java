import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] tamanhosTabela = {1000, 10000, 100000};
        int[] tamanhosDados = {10000, 100000, 1000000};
        int buscasPorConjunto = 5; // Número de buscas a realizar

        Random random = new Random(42); // Seed fixa para replicabilidade

        // Para cada função hash, criamos uma tabela separada de resultados
        for (int tipoHash = 1; tipoHash <= 3; tipoHash++) {
            String funcaoHash = tipoHash == 1 ? "Resto" :
                    tipoHash == 2 ? "Multiplicação" : "Dobramento";
            System.out.println("\n=== Resultados para Função Hash: " + funcaoHash + " ===");

            // Iterar pelos tamanhos da tabela hash
            for (int tamanhoTabela : tamanhosTabela) {
                System.out.println("\n-> Tabela de tamanho " + tamanhoTabela + ":");

                // Iterar pelos tamanhos de conjuntos de dados
                for (int tamanhoDados : tamanhosDados) {
                    Registro[] dados = gerarDados(tamanhoDados, random);
                    TabelaHash tabela = new TabelaHash(tamanhoTabela);

                    // Inserção
                    long inicioInsercao = System.nanoTime();
                    for (Registro reg : dados) {
                        tabela.inserir(reg, tipoHash);
                    }
                    long fimInsercao = System.nanoTime();
                    long tempoInsercao = fimInsercao - inicioInsercao;

                    // Busca
                    long totalTempoBusca = 0;
                    int totalComparacoes = 0;
                    for (int i = 0; i < buscasPorConjunto; i++) {
                        int chaveBusca = dados[random.nextInt(tamanhoDados)].getCodigo();
                        long inicioBusca = System.nanoTime();
                        boolean encontrado = tabela.buscar(chaveBusca, tipoHash);
                        long fimBusca = System.nanoTime();

                        totalTempoBusca += (fimBusca - inicioBusca);
                        totalComparacoes += tabela.getComparacoes();
                        tabela.resetComparacoes();
                    }

                    // Mostrar resultados
                    System.out.println("  Conjunto de " + tamanhoDados + " registros:");
                    System.out.println("    Tempo de Inserção: " + tempoInsercao + "ns");
                    System.out.println("    Colisões: " + tabela.getColisoes());
                    System.out.println("    Tempo Médio de Busca: " + (totalTempoBusca / buscasPorConjunto) + "ns");
                    System.out.println("    Número Médio de Comparações: " + (totalComparacoes / buscasPorConjunto));
                }
            }
        }
    }

    // Gerar dados com códigos únicos de 9 dígitos
    private static Registro[] gerarDados(int tamanho, Random random) {
        Registro[] dados = new Registro[tamanho];
        for (int i = 0; i < tamanho; i++) {
            int codigo = random.nextInt(900000000) + 100000000; // Garantir 9 dígitos
            dados[i] = new Registro(codigo);
        }
        return dados;
    }
}
