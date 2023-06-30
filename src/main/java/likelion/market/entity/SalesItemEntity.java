package likelion.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

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
    private String imageUrl;
    @NotNull
    private int minPriceWanted;
    @NotNull
    private String status;
    @NotNull
    private String writer;
    @NotNull
    private String password;
}
