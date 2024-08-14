package com.api.password_recovery.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


public enum BodyEmail {

     NEW_USER(
            """
                             Assunto: Bem-vindo(a) ao [Nome do Sistema]!<br></br>
                                        
                             Olá [Nome do Usuário],<br></br>
                                        
                             Estamos muito felizes em tê-lo(a) conosco! Obrigado(a) por criar uma conta no [Nome do Sistema]. Sua presença é muito importante para nós.<br></br>
                                        
                             Com sua nova conta, você terá acesso a [descrição dos principais benefícios e funcionalidades do sistema]. Estamos comprometidos em proporcionar a melhor experiência possível e em ajudá-lo(a) a alcançar seus objetivos.
                    """);

    private final String content;

    BodyEmail(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
