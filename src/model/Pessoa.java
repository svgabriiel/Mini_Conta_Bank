
package model;


import java.util.ArrayList;

public class Pessoa{
    private String nome;
    private int idade;
    private boolean logado;
    private String senha;
    private int conta;
    private double saldo;
    private int cpf;
    private String profession;
    private double salary;
    private ArrayList<Double> parcelas_R$ = new ArrayList<>(); 



     public Pessoa(){
    }
     public Pessoa(String nome, int cpf, int idade,  String senha, int conta) {
        this.setNome(nome);
        this.setIdade(idade);
        this.setCpf(cpf);
        this.setLogado(true);
        this.setSaldo(0);
        this.setSenha(senha);
        this.setConta(conta);
    
    }
    public void setParcelas_R$(double valor, int qtd) {
        for(int i = 0; i<qtd; i++)
            this.parcelas_R$.add(valor);
    }
   public int qtd_Parcela(){
        return this.parcelas_R$.size();
   } 
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public String getProfession() {
        return profession;
    }
    public double getSalary() {
        return salary;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }
    public int getCpf() {
        return cpf;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setNome(String n){
        this.nome = n;
    }
    public void setIdade(int i){
        this.idade = i;
    }
    public String getNome(){
        return this.nome;
    }

    public int getIdade(){
        return this.idade;
    }

    public String getSenha() {
        return senha;
    }
    public int getConta() {
        return conta;
    }
    public void setConta(int conta) {
        this.conta = conta;
    }
    public boolean isLogado() {
        return logado;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setLogado(boolean logado) {
        this.logado = logado;
    }

   @Override
   public String toString() {
       return (
        getNome()+"\n"+
        getConta()+"\n"+
        getCpf()+"\n"+
        getIdade()+"\n"+
        isLogado()+"\n"+
        getSenha()+"\n"
       );
       
   }

   
}