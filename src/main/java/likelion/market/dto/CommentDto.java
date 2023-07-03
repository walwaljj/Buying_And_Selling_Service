/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-03
 */

package likelion.market.dto;

import likelion.market.entity.CommentEntity;
import likelion.market.entity.SalesItemEntity;
import lombok.Data;

@Data
public class CommentDto {
    private Integer id;
    private SalesItemEntity itemId; // sales_item id
    private String writer;
    private String password;
    private String content;
    private String reply;

    public static CommentDto fromEntity(CommentEntity entity){
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setItemId(entity.getItemId());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        dto.setContent(entity.getContent());
        dto.setReply(entity.getReply());
        return dto;
    }

}
