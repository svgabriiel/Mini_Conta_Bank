package dados;
import model.Pessoa;

public class BancoDados {

    private Pessoa[] dados = new Pessoa[5];

    public BancoDados() {
       dados[0] = new Pessoa("ADM",111,1,"111",1);
       dados[0].setLogado(true);
       dados[0].setSaldo(77.5f);
       dados[0].setSalary(2000);

       dados[4] = new Pessoa("ANA LARISSA",444,1,"111",5);
       dados[4].setLogado(true);
       dados[4].setSaldo(-10);


    }
    public int getLength(){
        return dados.length;
    }
    public void atualizarDados(Pessoa user){
        dados[user.getConta()-1] = user;
    }
    public void apagarConta(int conta){
        this.dados[conta-1] = new Pessoa();
    }
    public Pessoa getUser(int conta){
        try{
            return dados[conta-1];

        }catch(Exception e){
            return null;
        }
    }

    public void setDados(Pessoa user,int index ) {
        dados[index] = new Pessoa(user.getNome(),user.getCpf(),
         user.getIdade(), user.getSenha(), user.getConta());
    }
    
    public boolean getLivre(int index){
        return (dados[index] ==null);

    }

    public boolean buscaUser(int cpf){
        for(int i = 0; i< dados.length;i++){
            if(getUser(i) == null) continue;
            if(getUser(i).getCpf() == cpf) return true;
        }
        return false;
    }

}