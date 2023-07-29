package likelion.market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany
    private List<SalesItemEntity> salesItem;
    @OneToMany
    private List<NegotiationEntity> negotiation;
    @OneToMany
    private List<CommentEntity> comment;
}
