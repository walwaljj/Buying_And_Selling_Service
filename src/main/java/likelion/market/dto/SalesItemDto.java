package likelion.market.dto;

import likelion.market.entity.SalesItemEntity;
import lombok.Data;

@Data
public class SalesItemDto {

    private Integer id;
    private String title;
    private String description;
    private int minPriceWanted;
    private String writer;
    private String password;


    public static SalesItemDto fromEntity(SalesItemEntity entity){
        SalesItemDto dto = new SalesItemDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setMinPriceWanted(entity.getMinPriceWanted());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
