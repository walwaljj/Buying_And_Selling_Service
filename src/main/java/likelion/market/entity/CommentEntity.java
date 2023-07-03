package likelion.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private SalesItemEntity itemId; // sales_item id
    private String writer;
    private String password;
    private String content;
    private String reply;
}
