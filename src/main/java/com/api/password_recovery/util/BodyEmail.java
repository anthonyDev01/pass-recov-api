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
                             <br></br>
                             <br></br>
                             <div style="
                                     background-color: greenyellow;
                                     width: fit-content;
                                     margin: auto;
                                     padding: 0.8rem;
                                     border-radius: 0.8rem;
                                     font-weight: bold;
                                     cursor: pointer;
                                    "
                             >
                                 <a href="%s" style="text-decoration: none;">CONFIRMAR E-MAIL</a>
                             </div>
                    """),
    PASSWORD_RECOV(
            """
                            Assunto: Código de Recuperação de Senha
                            <br></br>
                          
                            Olá,
                            
                            <br></br>
                            
                            Recebemos uma solicitação para redefinir sua senha. Para continuar o processo, use o código de recuperação abaixo:
                            <br></br>
                            
                            %s
                            
                            <br></br>                 
                            
                            Se você não solicitou a redefinição de senha, por favor, ignore este e-mail ou entre em contato com nosso suporte.
                            
                            Atenciosamente,
                            %s
                            
                            
                    """);

    private final String content;

    BodyEmail(String content) {
        this.content = content;
    }

    public String getContent(Object... params) {
        return String.format(content, params);
    }

}
