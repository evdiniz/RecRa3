public class TabelaHash {
    private No[] tabela; // Vetor para armazenamento
    private int tamanho; // Tamanho da tabela
    private int colisoes; // Número de colisões
    private int comparacoes; // Contador de comparações realizadas durante busca

    // Construtor
    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new No[tamanho];
        this.colisoes = 0;
        this.comparacoes = 0;
    }

    // Inserir elemento na tabela
    public void inserir(Registro reg, int tipoHash) {
        int posicao = calcularHash(reg.getCodigo(), tipoHash);

        No novo = new No(reg);
        if (tabela[posicao] == null) {
            tabela[posicao] = novo;
        } else {
            colisoes++;
            No atual = tabela[posicao];
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novo;
        }
    }

    // Buscar elemento na tabela
    public boolean buscar(int chave, int tipoHash) {
        int posicao = calcularHash(chave, tipoHash);
        No atual = tabela[posicao];
        comparacoes = 0; // Resetar comparações no início da busca

        while (atual != null) {
            comparacoes++;
            if (atual.dado.getCodigo() == chave) {
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    // Funções hash
    private int calcularHash(int chave, int tipoHash) {
        switch (tipoHash) {
            case 1: // Resto
                return chave % tamanho;
            case 2: // Multiplicação
                double A = 0.6180339887;
                int hash = (int) ((chave * A - (int) (chave * A)) * tamanho);
                return hash < 0 ? -hash : hash;
            case 3: // Dobramento
                String s = String.valueOf(chave);
                int soma = 0;
                for (int i = 0; i < s.length(); i += 2) {
                    int parte = Integer.parseInt(s.substring(i, Math.min(i + 2, s.length())));
                    soma += parte;
                }
                return soma % tamanho;
            default:
                return 0;
        }
    }

    // Métodos para obter métricas
    public int getColisoes() {
        return colisoes;
    }

    public int getComparacoes() {
        return comparacoes;
    }

    // Resetar contador de comparações
    public void resetComparacoes() {
        this.comparacoes = 0;
    }
}
