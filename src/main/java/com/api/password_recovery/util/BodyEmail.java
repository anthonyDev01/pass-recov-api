package com.api.password_recovery.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


public enum BodyEmail {

     NEW_USER(
            """
                             Assunto: Bem-vindo(a) ao %s!<br></br>
                                        
                             Olá %s,<br></br>
                                        
                             Estamos muito felizes em tê-lo(a) conosco! Obrigado(a) por criar uma conta no %s. Sua presença é muito importante para nós.<br></br>
                                        
                             Com sua nova conta, você terá acesso a todas funcionalidades. Estamos comprometidos em proporcionar a melhor experiência possível e em ajudá-lo(a) a alcançar seus objetivos.
                    """);

    private final String content;

    BodyEmail(String content) {
        this.content = content;
    }

    public String getContent(Object... params) {
        return String.format(content, params);
    }

}
