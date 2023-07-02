/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-02
 */
package likelion.market.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import likelion.market.entity.SalesItemEntity;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSalesItemPageDto {

    private Integer id;
    private String title;
    private String description;
    private int minPriceWanted;
    private String imageUrl;
    private String status;

    public static ResponseSalesItemPageDto fromEntity(SalesItemEntity entity){
            ResponseSalesItemPageDto pageDto = new ResponseSalesItemPageDto();
            pageDto.setId(entity.getId());
            pageDto.setTitle(entity.getTitle());
            pageDto.setDescription(entity.getDescription());
            pageDto.setMinPriceWanted(entity.getMinPriceWanted());
            pageDto.setImageUrl(entity.getImageUrl());
            pageDto.setStatus(entity.getStatus());
            return pageDto;
    }

}
