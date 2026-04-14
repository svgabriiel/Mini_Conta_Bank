package model;

public class Credito {
        protected int parcela;
        protected double Valor_R$;


        public int getParcela() {
            return parcela;
        }
        public double getValor_R$() {
            return Valor_R$;
        }
        public void setParcela(int parcela) {
            this.parcela = parcela;
        }
        public void setValor_R$(double valor_R$) {
            Valor_R$ = valor_R$;
        }
}
