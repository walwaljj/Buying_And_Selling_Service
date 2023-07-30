/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-03
 */

package likelion.market.dto;

import likelion.market.entity.CommentEntity;
import likelion.market.entity.SalesItemEntity;
import likelion.market.entity.UserEntity;
import lombok.Data;

/**
 * CommentDto class
 */
@Data
public class CommentDto {
    private Integer id;
    private SalesItemEntity itemId; // sales_item id
    private String writer;
    private String password;
    private String content;
    private String reply;

    private UserEntity user;

    /** 필요한 정보만 dto로 변환
     *
     * @param entity        Comment 정보
     * @return CommentEntity -> CommentDto 변환
     */
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
