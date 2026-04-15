package servico;
import dados.BancoDados;
import controle.InterfaceBank;
import model.*;


public class  ServicoBank implements InterfaceBank {
    public Pessoa user;
    private BancoDados banco = new BancoDados();
    
    @FunctionalInterface
    public interface InnerLambda {
        void run();
    }

    public void executar(InnerLambda l){
        l.run();
    }

    public boolean realiarEprestimo(double R$_parcela,int qtd , double capitalR$){

        if(R$_parcela <=0 || qtd <=0||capitalR$<=0)return false;

        user.setParcelas_R$(R$_parcela, qtd);
        user.setSaldo(user.getSaldo()+capitalR$);

        banco.atualizarDados(user);


        return true;
    }
    @Override
    public double[] solicitarEmprestimo(double valor, int qtd_Parcela){
            // retorno codigo de erro
            if(valor <= 0|| qtd_Parcela <=0) return new double[]{-2};
            double valor_min = 450;
        
            if(valor < valor_min) return new double[]{-2,-2,-2};
            

        int tentativas = 6;

        while (tentativas > 0) {
            double parcela = valor / qtd_Parcela;
            parcela += parcela*0.13;


            if(parcela <= user.getSalary()*0.3) return new double[]{1,parcela,valor};

            if(parcela >= user.getSalary()*0.6) return new double[]{-2,-2,-2};
            
            valor *=0.9;
            tentativas--;

        }

            return new double[]{-2,-2,-2}; 
        
    }
    @Override
    public boolean  setProfision(String profision, double salary){
            if(profision == null || salary <= 0) return false;

            user.setProfession(profision);
            user.setSalary(salary);
            banco.atualizarDados(user);
            return true;
    }

    @Override
        public int cadastrar(String nome, int cpf,int idade, String senha){
            if(nome == null|| cpf <=0|| idade <=0||senha == null){
                return -1;
            }
            
            if(!buscaUser(cpf)){ 
                for(int index = 0; index< banco.getLength();index++){
                    if(banco.getLivre(index)){
                        banco.setDados( new Pessoa(nome , cpf, idade, senha, index+1), index);
                        return index;
                        }
                    }
                }
                
            else return -2;

            return -1;
            
        }
    public void carregarUser(int conta){
        this.user = banco.getUser(conta);
    }
    @Override
    public boolean entrar(String senha, int conta){
        
           try {
            Pessoa aux = banco.getUser(conta);
        
             if(senha.equals(aux.getSenha())){
                carregarUser(conta);
                return true;}
              
            return false;
             
             
           } catch (Exception e) {
            return false;
           } 
    }
    public boolean validarCredenciais(Pessoa p, String nome, int cpf){

        if (p == null || nome == null) return false;
   
        return (p.getNome().equalsIgnoreCase(nome.trim()) && cpf == p.getCpf());
    }
    @Override
    public boolean validarConta(String nome,int conta, int cpf, String novaSenha){
         
       if (conta <= 0 || conta > banco.getLength() || nome == null || cpf == 0) 
            return false;

       Pessoa p = banco.getUser(conta);
   
        if(validarCredenciais(p, nome, cpf)) 
            return atualizarSenha(conta, novaSenha);
                 
        return false;
    }
 
    @Override
    public  boolean atualizarSenha(int conta, String novaSenha){
            Pessoa userAuxiliar = banco.getUser(conta);
  
            if(userAuxiliar.isLogado()){
                userAuxiliar.setSenha(novaSenha);
                banco.atualizarDados(userAuxiliar);
                return true;
            }
            else{
                return false;
            }
    }
     @Override
    public boolean depositar(double valor){
        
        if(valor<= 0) return false;

        else{
            this.user.setSaldo(user.getSaldo()+valor);
            banco.atualizarDados(this.user);
            return true;
        }
        
    }
    @Override
    public int existeConta(int conta){

            if(conta<=0 ||conta> banco.getLength()) return 0;

            Pessoa userAux = banco.getUser(conta);
            if(userAux == null) return 0;
            
            if(userAux.getCpf() == user.getCpf()) return -1;

            if(userAux != null && userAux.isLogado() ){
                return 1;
            }

            
            return 0;
    }

    @Override
    public  double getSaldo(){
            Pessoa userAuxiliar = banco.getUser(user.getConta());
            if(!userAuxiliar.isLogado()){
                return 0;
            }
            else{
                return userAuxiliar.getSaldo();
            }
    }

    private boolean validarTransferencia(int contaOrig , int contaDest, double valor){
        if(contaDest <= 0 || contaDest > banco.getLength()){
            return false;
        }

        if(existeConta(contaDest) != 1){
            return false;
        }

        if(valor <= 0){
            return false;
        }

        Pessoa userAux = banco.getUser(contaDest);
        if(user.getCpf() == userAux.getCpf() || user.getConta() == userAux.getConta()){
            return false;
        }

    
        if(user.getSaldo() < valor){
            return false;
        }
        
        return true;
        
    }
    @Override
    public boolean transferir(int contaDestino, double valor){

       if(!validarTransferencia(user.getConta(), contaDestino, valor)){
        return false;
       }
        Pessoa userAuxiliar = banco.getUser(contaDestino);
        user.setSaldo(user.getSaldo() - valor);
        userAuxiliar.setSaldo(userAuxiliar.getSaldo() + valor);

        banco.atualizarDados(user);
        banco.atualizarDados(userAuxiliar);

        return true;
    }

    public String getNomeContaPix(int conta){
        Pessoa aux = banco.getUser(conta);
        return aux.getNome();
    }
    public void sair(){
        this.user = new Pessoa();
    }
    public boolean buscaUser(int cpf){
        return banco.buscaUser(cpf);
    }
    public boolean remover(int conta){
            Pessoa userAuxiliar = banco.getUser(conta);
            if(!userAuxiliar.isLogado()){
                return false;
            }
            else{
                if(userAuxiliar.getSaldo() <0 || userAuxiliar.getSaldo() >0){
                    return false;
                }
                banco.apagarConta(conta);
                return true;
            }
         }
    }


