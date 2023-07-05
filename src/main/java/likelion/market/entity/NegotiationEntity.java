/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-04
 */

package likelion.market.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@Entity
public class NegotiationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonBackReference
    private SalesItemEntity itemId; // sales_item id
    @Column(name = "suggested_price")
    private int suggestedPrice;

    private String status;
    @NotNull
    private String writer;
    @NotNull
    private String password;
}
