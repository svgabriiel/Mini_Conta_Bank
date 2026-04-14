package controle;

public interface InterfaceBank{

    double[] solicitarEmprestimo(double valor, int qtd_Parcela);

    boolean  setProfision(String profision, double salary);

    boolean entrar(String senha, int conta);

    int cadastrar(String nome, int cpf,int idade, String senha);

    boolean transferir(int contaDestino, double valor);

    boolean depositar(double valor);

    double getSaldo();
    
    boolean remover(int conta);

    boolean atualizarSenha(int conta, String novaSenha);

    boolean validarConta(String nome,int conta, int cpf, String novaSenha);

    int existeConta(int conta); //Retorna CODERRO


}