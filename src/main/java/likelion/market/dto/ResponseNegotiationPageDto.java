/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-04
 */

package likelion.market.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import likelion.market.entity.NegotiationEntity;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseNegotiationPageDto {

    private Integer id;
    private Integer suggestedPrice;
    private String status;

    public static ResponseNegotiationPageDto fromEntity(NegotiationEntity entity){
        ResponseNegotiationPageDto dto = new ResponseNegotiationPageDto();
        dto.setId(entity.getId());
        dto.setSuggestedPrice(entity.getSuggestedPrice());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
