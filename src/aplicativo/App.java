package aplicativo;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import servico.ServicoBank;

public class App extends JFrame {
    
    private ServicoBank serv = new ServicoBank();
    private boolean logado;

    public App() {
    setTitle("GBank");
    setSize(300, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    // getContentPane().setBackground(new Color(0, 0, 0));  COR FUNDO
    setLayout(new GridLayout(6, 1));

    telaInicio();
    
    setVisible(true);

    }

    private void telaProfissao(){
        getContentPane().removeAll();
        setLayout(new GridLayout(10,3));
        JLabel titulo = new JLabel("Profissão"); titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setForeground(Color.BLUE);
        JLabel msg = new JLabel(); msg.setHorizontalAlignment(JLabel.LEFT);
        JTextField professionField = new JTextField(20);
        JTextField salaryField = new JTextField(5);
        JButton back = new JButton("Voltar");
        JButton confirm = new JButton("Confirmar");


        confirm.addActionListener(l->{
            double salary = -10;
            String profission = professionField.getText();
            try{
                salary = Double.parseDouble(salaryField.getText());
            } catch (Exception e) {
                msg.setText("ERRO");
                msg.setForeground(Color.RED);
                profission = null;
            }

            if(serv.setProfision(profission,salary)){
                msg.setForeground(new Color(0, 100, 0));
                msg.setText("SUCESSO!");
            }
        });


        back.addActionListener(l-> telaMaisOpcoes());

        add(titulo);
        add(msg);
        add(new JLabel("Profissão: "));
        add(professionField);
        add(new JLabel("Salário: "));
        add(salaryField);
        add(confirm);

        add(back);
        revalidate();
        repaint();
    }

    private void telaExtrato(){

        getContentPane().removeAll();
        setLayout(new GridLayout(10,3));
        JLabel titulo = new JLabel("Extrato Bancário");
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setForeground(Color.BLUE);

        JButton back = new JButton("BACK");

        back.addActionListener(l->telaMaisOpcoes());


        add(titulo);
        add(back);
        revalidate();
        repaint();
    }

    private void telaMaisOpcoes(){
    getContentPane().removeAll();
    setLayout(new GridLayout(10,3));
    JLabel titulo = new JLabel("Mais Opções");
    titulo.setHorizontalAlignment(JLabel.CENTER);
    titulo.setForeground(Color.BLUE);

    JButton back = new JButton("Voltar");
    JButton profission = new JButton("Profissão");
    JButton extrato = new JButton("Extrato");

    back.addActionListener(l-> telaApp());
    profission.addActionListener(l-> telaProfissao());

    extrato.addActionListener(l -> telaExtrato());


    add(titulo);
    add(profission);
    add(extrato);
    add(back);
    revalidate();
    repaint();
    }

    private void telaEmprestimos(){
        getContentPane().removeAll();
        setLayout(new GridLayout(10,1));

        JLabel titulo = new JLabel("Emprestimos");
        JTextField valor$Field = new JTextField(10);
        JTextField parcelasField = new JTextField(4);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setForeground(Color.BLUE);
        JButton back =new JButton("Back");
        JButton solicitar = new JButton("Solicitar");
        JButton realizar = new JButton("Realizar");
        realizar.setEnabled(false);
        JLabel msg1 = new JLabel();
        JLabel msg2 = new JLabel();
        back.setForeground(Color.blue);
        back.setBackground(Color.black);
        double[] result = new double[4];
        //[0]=codERR, [1]=ParcelasR$, [2]=capitalR$,[3]=QTD_Parcelas


        solicitar.addActionListener(l->{
            double valor = 0;
            int qtdParc = 0;
            double[] resultAux = new double[3];

            try {
                valor = Double.parseDouble(valor$Field.getText());
                qtdParc = Integer.parseInt(parcelasField.getText());
                
                   resultAux = serv.solicitarEmprestimo(valor, qtdParc);
                   for(int i= 0; i< 3 ; i++){
                    result[i] = resultAux[i];
                    System.out.println(result[i]);
                   }
                  

                if((int)result[0] == 1){
                    result[0]= qtdParc;
                    msg1.setText("Valor liberado: "+ result[2]);
                    msg2.setText("Parcelas minimas de "+ result[1] + " em "+ qtdParc);
                    msg2.setForeground(new Color(0, 100, 0));
                    msg2.setHorizontalAlignment(JLabel.LEFT);
                    msg1.setForeground(new Color(0, 100, 0));
                    msg1.setHorizontalAlignment(JLabel.LEFT);
                    realizar.setEnabled(true);
                }
                else{
                    msg2.setText("Não foi aprovado");
                    msg2.setForeground(new Color(255, 215, 0));
                    msg2.setHorizontalAlignment(JLabel.LEFT);
                }
            } catch (Exception e) {
                msg2.setText("ERRO");
                msg2.setForeground(Color.RED);
                msg2.setHorizontalAlignment(JLabel.LEFT);
                
            }        
        });


               realizar.addActionListener(l->{ 

                 if(serv.realiarEprestimo(result[1],(int)result[0], result[2])){
                    msg1.setText("Emprestimo realizado com sucesso!: R$"+ result[2]);
                    msg2.setText("Parcelas de "+ result[1]);
                    msg2.setForeground(new Color(0, 100, 0));
                    msg2.setHorizontalAlignment(JLabel.LEFT);
                    msg1.setForeground(new Color(0, 100, 0));
                    msg1.setHorizontalAlignment(JLabel.LEFT);
                    realizar.setEnabled(false);
                }
                    
                });


        back.addActionListener(l->telaApp());
        add(titulo);
        add(msg1);
        add(msg2);
        add(new JLabel("Valor do emprestimo"));
        add(valor$Field);
        add(new JLabel("Numero de Parcelas"));
        add(parcelasField);
        add(realizar);
        add(solicitar);
        add(back);
        revalidate();
        repaint();
    }
    

    private void telaComprovantePix(JLabel mensagem,JLabel msg){
        getContentPane().removeAll();
        setLayout(new GridLayout(8,1));
        JLabel titulo = new JLabel("COMPROVANTE");
        titulo.setForeground(Color.RED);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        JButton novoPix = new JButton("Novo Pix");
        JButton back = new JButton("←");
        
        msg.setHorizontalAlignment(JLabel.LEFT);
        msg.setForeground(Color.BLUE);
        novoPix.addActionListener(l -> telaPrincipalPix());
        back.addActionListener(l -> telaApp());

        mensagem.setForeground(new Color(0,100,0));
        
        add(titulo);
        add(msg);
        add(mensagem);
        add(novoPix);
        add(back);
        
        revalidate();
        repaint();
    }
  
    private void telaPrincipalPix(){
        getContentPane().removeAll();
        setLayout(new GridLayout(10,1));
        
        JLabel titulo = new JLabel("Area PIX");
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setForeground(Color.BLUE);
        JTextField contaField = new JTextField(5);
        JTextField valorField = new JTextField(10);
        JButton back = new JButton("BACK");
        JButton transferir = new JButton("Transferir");
        transferir.setEnabled(false);
        JButton buscaUser = new JButton("Buscar");
       
        JLabel msg = new JLabel();
        JLabel informa = new JLabel();
        JLabel textoValor = new JLabel("Valor: ");
        textoValor.setForeground(Color.MAGENTA);


        buscaUser.addActionListener(l ->{
            int conta;
           String nome;
            try {
                conta = Integer.parseInt(contaField.getText());
                
                int CODERR = serv.existeConta(conta);
                if(CODERR == 1){
                    transferir.setEnabled(true);
                    nome = serv.getNomeContaPix(conta);
                    informa.setText("Nome: "+nome);
                    msg.setText("");
              
                    
                }
                 else if(CODERR == 0) {
                    msg.setText("Conta não existente");
                    informa.setText("");
                    transferir.setEnabled(false);
                 }
                 else if(CODERR == -1) {
                    msg.setText("[ERRO] -> Não pode ser própria conta");
                    msg.setForeground(Color.RED);
                    informa.setText("");
                    transferir.setEnabled(false);
                 }
                
            } catch (Exception e) {
                msg.setText("[ERRO] -> Não se aplica");
                msg.setForeground(Color.RED);
                informa.setText("");
            }

        });
        
        transferir.addActionListener(l ->{
            double valor = -1;
            int conta = -1;
            try {
                conta = Integer.parseInt(contaField.getText());
                valor = Double.parseDouble(valorField.getText());
                if(serv.transferir(conta, valor)){
                    msg.setText("Pix Realizado! R$ "+ valor);
                    msg.setForeground(Color.GREEN);
                    telaComprovantePix(msg,new JLabel(serv.getNomeContaPix(conta)));

                }
                else {
                    msg.setText("Pix Falhou!");
                    msg.setForeground(Color.RED);
                };

            } catch (Exception e) {
                msg.setText("[ERRO] -> Não se aplica");
                msg.setForeground(Color.RED);
            }
        });
                
        back.addActionListener(l -> telaApp());
        JLabel texto = new JLabel("Chave Pix: ");
        texto.setForeground(Color.MAGENTA);

        
        add(titulo);
        add(texto);
        add(contaField);
        add(informa);
        add(textoValor);
        add(valorField);
        add(msg);
       
        add(buscaUser);
        add(transferir);
        
        add(back);
        
        revalidate();
        repaint();
}
    private void telaApp(){
        getContentPane().removeAll();
        setLayout(new GridLayout(10,3));
        JLabel titulo = new JLabel("GBank");
        titulo.setForeground(Color.BLUE);
        JLabel nome = new JLabel("  "+ serv.user.getNome()+"   ");
        JLabel conta = new JLabel("  Conta "+ serv.user.getConta()+"   ");
        JLabel saldo = new JLabel("  Saldo "+ serv.user.getSaldo()+"   ");
        JButton deposito = new JButton("Deposito");
        JButton pix = new JButton("PIX");
        JButton sair = new JButton("Logout");
        JButton emprestimo = new JButton("Emprestimos");
        JButton opition = new JButton("Mais Opções");

        opition.addActionListener(l-> telaMaisOpcoes());
        deposito.addActionListener( l -> deposito());
        pix.addActionListener(l -> telaPrincipalPix());
        sair.addActionListener(l -> {serv.user = null; telaInicio();});
        emprestimo.addActionListener(l-> telaEmprestimos());
     
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setForeground(Color.BLUE);
        conta.setHorizontalAlignment(JLabel.RIGHT);
        nome.setHorizontalAlignment(JLabel.LEFT);
        saldo.setHorizontalAlignment(JLabel.RIGHT);

        if(serv.user.getSaldo() > 0){
            saldo.setForeground(new Color(0, 100, 0));
        } 
        else if(serv.user.getSaldo() <0)
            saldo.setForeground(Color.RED);

         add(titulo);
         add(nome);
         add(conta);
         add(saldo);
         add(emprestimo);
         add(deposito);
         add(pix);
         add(opition);
         add(sair);
    
        revalidate();
        repaint();
    }

    private void deposito(){
        getContentPane().removeAll();
        
        JLabel titulo = new JLabel(" Área Depósito ");
        titulo.setForeground(Color.BLUE);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        JLabel saldo = new JLabel("Saldo: "+ serv.user.getSaldo());
        JButton finalizar = new JButton("Depositar");
        JButton voltar = new JButton("Voltar");
        JTextField valorField = new JTextField(10);
        JLabel msg = new JLabel();
       

        finalizar.addActionListener(l ->{
            double v= 0;
            try {
                v = Double.parseDouble(valorField.getText());
                if(serv.depositar(v)){
                    msg.setText("Depósito realizado com sucesso");
                    msg.setForeground(new Color(0,100,0));
                    finalizar.setEnabled(false);
                }
                else{
                    msg.setText("[ERRO] -> Valor precisa ser maior que 0");
                    msg.setForeground(Color.RED);
                }
            } catch (Exception e) {
                msg.setText("ERRO");
                msg.setForeground(Color.RED);
            }

           
        });
        voltar.addActionListener(l-> telaApp());

       
    add(titulo);
    add(saldo);
    add(new Label("Digite o valor a ser depositado"));
    add(valorField);
    add(finalizar);
    add(msg);
    add(voltar);

    revalidate();
    repaint();
    }
   
    private void telaInicio() {
    getContentPane().removeAll();
    setLayout(new GridLayout(7, 1));
    JLabel titulo = new JLabel("Bem vindo ao GBank");
    // titulo.setForeground(new Color(0,110,0));
    titulo.setForeground(Color.BLUE);
    titulo.setHorizontalAlignment(JLabel.CENTER);
    JButton loginBT = new JButton("Login");
    JButton cadastrarBT = new JButton("Nova Conta");
    

    loginBT.addActionListener(e -> { serv.sair(); telaLogin();});
    cadastrarBT.addActionListener(e -> telaCadastro());
    

    add(titulo);
    add(new JLabel(""));
    add(loginBT);
    add(cadastrarBT);
    

    revalidate();
    repaint();
}
    
    private void telaNovaSenha(){
        getContentPane().removeAll();
        setLayout(new GridLayout(15,2));
        JLabel titulo = new JLabel("Restaurar Senha");
        titulo.setForeground(Color.BLUE);
        JButton back = new JButton("BACK");
        JButton restaurar = new JButton("Restaurar");
        JTextField nomeField = new JTextField(5);
        JTextField contaField = new JTextField(5);
        JTextField cpfField = new JTextField(5);
        JPasswordField senhaField = new JPasswordField(8);
        JLabel msg = new JLabel();


        restaurar.addActionListener(l ->{
            String nome = new String();
            String senha = new String();
            int conta = 0;
            int cpf= 0;
        
            try {
                nome = nomeField.getText();
                conta = Integer.parseInt(contaField.getText());
                cpf = Integer.parseInt(cpfField.getText());
                senha =  new String(senhaField.getPassword());
            } catch (Exception e) {
               msg.setText("Erro!");
            }
            if(serv.validarConta(nome, conta, cpf, senha)){
                msg.setText("Senha Atualizada com SUCESSO");
                restaurar.setEnabled(false);

            }
            else
                msg.setText("Falha! Credenciais Incorreta!");
            
        });

        back.addActionListener(l -> telaInicio());

        titulo.setHorizontalAlignment(JLabel.CENTER);
        add(titulo);
        add(new JLabel("  NOME:"));
        add(nomeField);
        add(new JLabel("  CONTA:"));
        add(contaField);
        add(new JLabel("  CPF:"));
        add(cpfField);
        add(new JLabel("  NOVA SENHA:"));
        add(senhaField);
        add(msg);

        add(restaurar);
        add(back);
    
        revalidate();
        repaint();
    }

    private void telaCadastro() {
        getContentPane().removeAll();
        setLayout(new GridLayout(13, 1));

        JLabel titulo = new JLabel("Nova Conta");
        titulo.setForeground(Color.BLUE);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        JTextField nome = new JTextField(10);
        JTextField idade = new JTextField(5);
        JTextField cpf = new JTextField(5);
        JPasswordField senha = new JPasswordField(10);
        JButton cadastrar = new JButton("Cadastrar");
        JButton voltar = new JButton("Voltar");
        JLabel msg = new JLabel();

        cadastrar.addActionListener(e -> {
        String n = nome.getText();
        String s = new String(senha.getPassword());
        int i,cpf_;
        int codERR;

        try {
            i = Integer.parseInt(idade.getText());
            cpf_= Integer.parseInt(cpf.getText());

        } catch(Exception ex) {
            n =null; s= null; i = 0; cpf_= 0;
            msg.setText("ERRO");
            return;
        }
       
        codERR = serv.cadastrar(n,cpf_, i, s);
        
        if(codERR ==-1)
             msg.setText("ERRO");
        else if(codERR == -2)
            msg.setText("Conta ja cadastrada!");
        else 
            msg.setText("Conta N° "+(codERR));
    });
    
    voltar.addActionListener(e -> telaInicio());

    add(titulo);
    add(new JLabel("Nome:"));
    add(nome);
    add(new JLabel("CPF:"));
    add(cpf);
    add(new JLabel("Idade:"));
    add(idade);
    add(new JLabel("Senha:"));
    add(senha);
    add(cadastrar);
    add(voltar);
    add(msg);

    revalidate();
    repaint();
}

    private void telaLogin() {
    getContentPane().removeAll();
    setLayout(new GridLayout(10,2));
    JLabel titulo = new JLabel("Login");
    titulo.setForeground(Color.BLUE);
    JTextField contaField = new JTextField(5);
    JPasswordField senhaField = new JPasswordField(10);
    JButton entrar = new JButton("Entrar");
    JButton voltar = new JButton("Voltar");
    JLabel msg = new JLabel();
    JButton senhanova = new JButton("Esqueci senha");

    entrar.addActionListener(e -> {
        int conta;

        try {   
            conta = Integer.parseInt(contaField.getText());
        } catch(Exception ex) {
            msg.setText("Conta inválida");
            return;
        }

        String senha = new String(senhaField.getPassword());

        logado = serv.entrar(senha, conta);
        if(logado) {
            telaApp();
        }
        else 
        msg.setText("Falha");
    });

    voltar.addActionListener(e -> telaInicio());
    senhanova.addActionListener(L-> telaNovaSenha());
    titulo.setHorizontalAlignment(JLabel.CENTER);
    add(titulo);
    add(new JLabel("   Conta:"));
    add(contaField);
    add(new JLabel("   Senha:"));
    add(senhaField);
    add(entrar);
    add(senhanova);
    add(voltar);
    add(msg);

    revalidate();
    repaint();
}
    
}