/**
 * @project MiniProject_Basic_JungSyHyeon
 */

package likelion.market.contoller;

import likelion.market.dto.CommentDto;
import likelion.market.dto.ResponseCommentPageDto;
import likelion.market.dto.ResponseMessageDto;
import likelion.market.security.CustomUserDetailsManager;
import likelion.market.service.CommentService;
import likelion.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * CommentController class
 */

@RequestMapping("/items")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;



    /**게시글에 댓글 등록
     *u
     * @param itemId        게시글 Id
     * @param dto           요청 정보
     * @return  ResponseMessageDto 결과를 메세지로 반환
     * @since 2023-07-03
     */
    @PostMapping("/{itemId}/comments")
    public ResponseMessageDto create(@PathVariable Integer itemId, @RequestBody CommentDto dto){

        return commentService.createComment(itemId, dto);
    }


    /**
     * 게시글 id에 해당하는 댓글 전체 조회
     * @param itemId        게시글 Id
     * @param pageNumber    페이지 번호
     * @param pageSize      한 페이지에 담을 댓글 수
     * @return Page         결과를 페이지타입으로 반환
     * @since 2023-07-03
     */
    @GetMapping("/{itemId}/comments")
    public Page<ResponseCommentPageDto> readCommentAll(@PathVariable Integer itemId,
                                                       @RequestParam(value = "page",defaultValue = "0")int pageNumber,
                                                       @RequestParam (value = "limit", defaultValue = "25") int pageSize){
        return commentService.readCommentAll(itemId,pageNumber,pageSize);
    }


    /**
     * 게시글 id에 해당하는 댓글 업데이트
     * @param itemId        게시글 Id
     * @param commentId     댓글 Id
     * @param dto           요청 정보
     * @return  ResponseMessageDto      결과를 메세지로 반환
     * @throws IllegalAccessException   인증 실패시 예외
     * @since 2023-07-03
     */
    @PutMapping("/{itemId}/comments/{commentId}")
    public ResponseMessageDto updateComments(@PathVariable Integer itemId, @PathVariable Integer commentId,
                                     @RequestBody CommentDto dto) throws IllegalAccessException {
        return commentService.updateComment(itemId, commentId, dto);
    }


    /**
     * 게시글 id에 해당하는 댓글의 답변 업데이트
     * @param itemId        게시글 Id
     * @param commentId     댓글 Id
     * @param dto           요청 정보
     * @return  ResponseMessageDto      결과를 메세지로 반환
     * @throws IllegalAccessException   인증 실패시 예외
     * @since 2023-07-03
     */
    @PutMapping("/{itemId}/comments/{commentId}/reply")
    public ResponseMessageDto updateReply(@PathVariable Integer itemId, @PathVariable Integer commentId,
                                     @RequestBody CommentDto dto) throws IllegalAccessException {
        return commentService.updateReply(itemId, commentId, dto);
    }


    /** 댓글 삭제
     *
     * @param itemId        게시글 Id
     * @param commentId     댓글 Id
     * @param dto           요청 정보
     * @return  ResponseMessageDto      결과를 메세지로 반환
     * @throws IllegalAccessException   인증 실패시 예외
     * @since 2023-07-03
     */
    @DeleteMapping("/{itemId}/comments/{commentId}")
    public ResponseMessageDto deleteComments(@PathVariable Integer itemId, @PathVariable Integer commentId,
                                             @RequestBody CommentDto dto) throws IllegalAccessException {
        return commentService.deleteComment(itemId, commentId, dto);
    }
}
