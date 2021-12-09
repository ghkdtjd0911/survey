package com.natsuki.survey.model;

import com.natsuki.survey.common.Authority;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class RegisteredUser {
    @Id
    private String userId;
    private String pw;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "", targetEntity = Survey.class)
    private List<Survey> survey;

    @Builder
    public RegisteredUser(String userId, String pw, String userName, Authority authority) {
        this.authority = authority;
        this.pw = pw;
        this.userId = userId;
    }

}
