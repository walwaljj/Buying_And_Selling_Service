package likelion.market.dto;

import likelion.market.entity.NegotiationEntity;
import lombok.Data;

@Data
public class NegotiationDto {

    private Integer id;
    private Integer ItemId; // sales_item id
    private int suggested_price;
    private String status;
    private String writer;
    private String password;

    public static NegotiationDto fromEntity(NegotiationEntity entity){
        NegotiationDto dto = new NegotiationDto();
        dto.setId(entity.getId());
        dto.setItemId(entity.getItemId());
        dto.setSuggested_price(entity.getSuggested_price());
        dto.setStatus(entity.getStatus());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
