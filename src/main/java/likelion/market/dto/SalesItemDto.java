package likelion.market.dto;

import likelion.market.entity.SalesItemEntity;
import lombok.Data;

@Data
public class SalesItemDto {

    private Integer id;
    private String title;
    private String description;
    private String imageUrl;
    private int min_price_wanted;
    private String status;
    private String writer;
    private String password;


    public static SalesItemDto fromEntity(SalesItemEntity entity){
        SalesItemDto dto = new SalesItemDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImageUrl(entity.getImageUrl());
        dto.setMin_price_wanted(entity.getMin_price_wanted());
        dto.setStatus(entity.getStatus());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
