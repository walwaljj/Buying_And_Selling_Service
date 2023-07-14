/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-06-29
 */
package likelion.market.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import likelion.market.entity.SalesItemEntity;
import lombok.Data;

/**
 * SalesItemDto class
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalesItemDto {

    private Integer id;
    private String title;
    private String description;
    private int minPriceWanted;
    private String writer;
    private String password;
    private String status;

    /**필요한 정보만 dto 로 변환
     *
     * @param entity
     * @return  SalesItemEntity -> SalesItemDto
     */
    public static SalesItemDto fromEntity(SalesItemEntity entity){
        SalesItemDto dto = new SalesItemDto();
        dto.setTitle(entity.getTitle());
        dto.setMinPriceWanted(entity.getMinPriceWanted());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
