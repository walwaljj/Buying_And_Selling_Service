/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-04 연관관계 수정
 */

package likelion.market.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

/**
 * SalesItemEntity class
 */
@Getter
@Setter
@Entity
public class SalesItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String title;
    @NotNull
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @NotNull
    @Column(name = "min_price_wanted")
    private int minPriceWanted;
    @NotNull
    private String status;
    @NotNull
    private String writer;
    @NotNull
    private String password;
    @OneToMany(mappedBy = "itemId", fetch = FetchType.LAZY)
    private List<CommentEntity> comment;
    @OneToMany(mappedBy = "itemId", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<NegotiationEntity> negotiation;

}
