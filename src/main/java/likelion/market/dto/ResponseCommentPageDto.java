package likelion.market.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import likelion.market.entity.CommentEntity;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseCommentPageDto {

    private Integer id;
    private String content;
    private String reply;


    public static ResponseCommentPageDto fromEntity(CommentEntity commentEntity) {
        ResponseCommentPageDto responseCommentPageDto = new ResponseCommentPageDto();
        responseCommentPageDto.setId(commentEntity.getId());
        responseCommentPageDto.setContent(commentEntity.getContent());
        responseCommentPageDto.setReply(commentEntity.getReply());
        return responseCommentPageDto;
    }
}
