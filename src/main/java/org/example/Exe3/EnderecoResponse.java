package org.example.Exe3;

public class EnderecoResponse {
    private boolean erro; // true se CEP não encontrado

    public boolean isErro() {
        return erro;
    }

    public void setErro(boolean erro) {
        this.erro = erro;
    }
}

