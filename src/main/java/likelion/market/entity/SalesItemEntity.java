/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-06-29
 */

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
    @Column
    private String title;
    @NotNull
    @Column
    private String description;
    @Column
    private String imageUrl;
    @NotNull
    @Column
    private int minPriceWanted;
    @NotNull
    @Column
    private String status;
    @NotNull
    @Column
    private String writer;
    @NotNull
    @Column
    private String password;
}
