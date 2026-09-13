package com.rare.userservice.entity;

import com.rare.embeddable.Name;
import com.rare.enums.LoginMethod;
import com.rare.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;
    public String detailedName(){
        if(name.getMiddleName()==null) name.setMiddleName("");
        if(name.getSurname()==null) name.setSurname("");

        return name.getFirstName()+ " "+name.getMiddleName()+ " "+name.getSurname();
    }

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(unique = true)
    private String phone;

    @Builder.Default
    private LoginMethod loginMethod=LoginMethod.NORMAL;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role=UserRole.ROLE_USER;

    @Builder.Default
    private boolean enabled=true;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

}
