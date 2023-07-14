/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-04
 */

package likelion.market.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import likelion.market.entity.NegotiationEntity;
import lombok.Data;

/**
 * ResponseNegotiationPageDto class 조회시 페이지에서 보여줄 정보
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseNegotiationPageDto {

    private Integer id;
    private Integer suggestedPrice;
    private String status;

    /** 조회 시 필요한 정보만 Dto 로 변경
     *
     * @param entity        Negotiation 정보
     * @return  NegotiationEntity -> ResponseNegotiationPageDto
     */
    public static ResponseNegotiationPageDto fromEntity(NegotiationEntity entity){
        ResponseNegotiationPageDto dto = new ResponseNegotiationPageDto();
        dto.setId(entity.getId());
        dto.setSuggestedPrice(entity.getSuggestedPrice());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
