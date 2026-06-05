package io;

import java.nio.file.FileSystems;
import java.nio.file.Path;

// TODO: FINALIZAR
public abstract class Armazenamento {

    public static final Path DIR_RAIZ_ARQUIVOS;
    public static final Path ARQ_CONTAS_USUARIO;

    static {
        DIR_RAIZ_ARQUIVOS = FileSystems.getDefault().getPath("arquivos");
        ARQ_CONTAS_USUARIO = DIR_RAIZ_ARQUIVOS.resolve(
            Path.of("contas_usuario.csv")
        );
    }
}
