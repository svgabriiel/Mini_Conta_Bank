package dados;
import model.Pessoa;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class BancoDados {

    //private Pessoa[] dados = new Pessoa[5];
    private ArrayList<Pessoa> dados = new ArrayList<>();

    public BancoDados() {
       dados.add(new Pessoa("ADM",111,1,"111",1));

       dados.add( new Pessoa("ANA LARISSA",444,1,"111",2));

    }
    public int getLength(){
        return dados.size();
    }
    public void atualizarDados(Pessoa user){
        dados.add(user.getConta()-1,user);
    }
    public void apagarConta(int conta){
        dados.remove(conta-1);
    }
    public Pessoa getUser(int conta){
        try{
            return dados.get(conta-1);

        }catch(Exception e){
            return null;
        }
    }

    public void setDados(Pessoa user,int index ) {
        dados.add( index-1, user);
    }

    public boolean buscaUser(int cpf){
        for(int i = 0; i< dados.size();i++){
            if(dados.get(i).getCpf() == cpf) return true;
        }

        return false;
    }

}