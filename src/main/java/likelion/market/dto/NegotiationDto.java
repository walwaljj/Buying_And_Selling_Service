/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-04
 */

package likelion.market.dto;

import likelion.market.entity.SalesItemEntity;
import lombok.Data;

@Data
public class NegotiationDto {

    private Integer id;
    private SalesItemEntity itemId; // sales_item id
    private Integer suggestedPrice;
    private String status;
    private String writer;
    private String password;

}
