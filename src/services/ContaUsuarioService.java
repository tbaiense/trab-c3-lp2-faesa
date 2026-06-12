package services;

import io.Arquivos;
import modelos.Funcionario;

public class ContaUsuarioService {

    public static Funcionario[] obterTodos() {
        return Arquivos.Contas.ler_contas();
    }
}
