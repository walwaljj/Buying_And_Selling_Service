package likelion.market.entity;

import jakarta.persistence.*;
import likelion.market.enums.Role;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany
    private List<SalesItemEntity> salesItem;
    @OneToMany
    private List<NegotiationEntity> negotiation;
    @OneToMany
    private List<CommentEntity> comment;
}
