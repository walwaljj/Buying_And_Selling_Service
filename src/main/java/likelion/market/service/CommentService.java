/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-03
 */

package likelion.market.service;

import likelion.market.dto.CommentDto;
import likelion.market.dto.ResponseCommentPageDto;
import likelion.market.dto.ResponseMessageDto;
import likelion.market.entity.CommentEntity;
import likelion.market.entity.SalesItemEntity;
import likelion.market.repository.CommentRepository;
import likelion.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.List;

@Service @RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final SalesItemRepository salesItemRepository;

    /**댓글을 등록합니다.
     *
     * @param itemId    게시글 id
     * @param dto       댓글 정보
     * @return  ResponseMessageDto#getMessage() 성공 메세지 반환
     * @since 2023-07-03
     */

    public ResponseMessageDto createComment(Integer itemId, CommentDto dto) {
        CommentEntity entity = new CommentEntity();
        entity.setItemId(checkItemId(itemId));
        entity.setWriter(dto.getWriter());
        entity.setPassword(dto.getPassword());
        entity.setContent(dto.getContent());
        CommentDto.fromEntity(commentRepository.save(entity));

        return getResponseMessageDto("댓글이 등록되었습니다.");
    }

    /**
     * 댓글을 전체 조회합니다 (페이징)
     *
     * @param itemId    게시글 Id
     * @param pageNumber    페이지
     * @param pageSize      담을 게시글 수
     * @return  Page<ResponseSalesItemPageDto> 상품 게시글 id에 맞는 댓글을 Page 형태로 반환
     * @since 2023-07-03
     */
    public Page<ResponseCommentPageDto> readCommentAll(Integer itemId, int pageNumber, int pageSize) {
        SalesItemEntity salesItemEntity = checkItemId(itemId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<CommentEntity> comments = salesItemEntity.getComment();
        PageImpl<CommentEntity> commentsPage = new PageImpl<>(comments, pageable, comments.size());
        return commentsPage.map(ResponseCommentPageDto::fromEntity);
    }

    /** 댓글(Content)을 업데이트 합니다.
     *
     * @param itemId        게시글 Id
     * @param commentId     댓글 Id
     * @param dto           업데이트 댓글 정보
     * @return  ResponseMessageDto#getMessage() 성공 메세지 반환
     * @throws IllegalAccessException id, password 검증 실패 시 반환
     * @since 2023-07-03
     */
    public ResponseMessageDto updateComment(Integer itemId, Integer commentId, CommentDto dto) throws IllegalAccessException {
        // 댓글 id 확인
        CommentEntity commentEntity = checkUser(itemId, commentId, dto.getWriter(), dto.getPassword());

        //댓글 update
        commentEntity.setContent(dto.getContent());
        CommentDto.fromEntity(commentRepository.save(commentEntity));

        return getResponseMessageDto("댓글이 수정되었습니다.");
    }

    /**
     * 댓글에 대한 작성자의 답글(Reply)을 업데이트 합니다.
     *
     * @param itemId        게시글 Id
     * @param commentId     댓글 Id
     * @param dto           업데이트 답변 정보
     * @return ResponseMessageDto#getMessage() 성공 메세지 반환
     * @throws IllegalAccessException id, password 검증 실패 시 반환
     * @since 2023-07-03
     */
    public ResponseMessageDto updateReply(Integer itemId, Integer commentId, CommentDto dto) throws IllegalAccessException {
        // 게시글 id 확인
        SalesItemEntity salesItemEntity = checkItemId(itemId);

        if(!salesItemEntity.getWriter().equals(dto.getWriter()) || !salesItemEntity.getPassword().equals(dto.getPassword())){
            throw new IllegalAccessException("허용되지 않는 접근자 입니다.");
        }

        // 댓글 id 확인
        CommentEntity commentEntity = checkCommentId(salesItemEntity.getId(), commentId);
    
        // 답변 업데이트
        commentEntity.setReply(dto.getReply());
        CommentDto.fromEntity(commentRepository.save(commentEntity));

        return getResponseMessageDto( "댓글에 답변이 추가되었습니다.");
    }


    /**
     * 댓글을 삭제합니다.
     *
     * @param itemId        게시글 Id
     * @param commentId     댓글 Id
     * @param dto           댓글 정보
     * @return ResponseMessageDto#getMessage() 성공 메세지 반환
     * @throws IllegalAccessException id, password 검증 실패 시 반환
     * @since 2023-07-03
     */
    public ResponseMessageDto deleteComment(Integer itemId, Integer commentId, CommentDto dto) throws IllegalAccessException {
        CommentEntity commententity = checkUser(itemId, commentId, dto.getWriter(), dto.getPassword());
        commentRepository.deleteById(commententity.getId());
        return getResponseMessageDto("댓글을 삭제했습니다.");
    }

    /**
     * 게시글 존재 여부를 확인합니다.
     *
     * @param itemId    item Id
     * @throws ResponseStatusException   검증 실패 시 http 404 반환
     * @return 게시글이 존재한다면 SalesItemEntity 반환
     * @since 2023-07-03
     */
    public SalesItemEntity checkItemId(Integer itemId){
        Optional<SalesItemEntity> optionalSalesItemEntity = salesItemRepository.findById(itemId);
        if(!optionalSalesItemEntity.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return optionalSalesItemEntity.get();
    }

    /**
     * 결과에 대한 응답 메세지를 반환합니다.
     *
     * @param msg
     * @return ResponseMessageDto
     * @since 2023-07-03
     */
    private ResponseMessageDto getResponseMessageDto(String msg) {
        ResponseMessageDto response = new ResponseMessageDto();
        response.setMessage(msg);
        return response;
    }

    /**
     * 댓글 존재 여부를 확인하고, 있다면 해당 CommentEntity 를 반환합니다.
     *
     * @param itemId        게시글 id
     * @param commentId     답글 id
     * @return CommentEntity
     * @throws ResponseStatusException   itemId 에 해당하는 commentId 아닐 때 http 404 반환
     * @since 2023-07-03
     */
    private CommentEntity checkCommentId(Integer itemId, Integer commentId){
        // 게시글 id 확인
        SalesItemEntity salesItemEntity = checkItemId(itemId);
        // 게시글에 존재하는 comment를 모두 가져옴.
        List<CommentEntity> comment = salesItemEntity.getComment();
        //
        return comment.stream().filter(commentEntity -> commentEntity.getId().equals(commentId))
                .findFirst().orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);});
    }

    /**
     * 작성자 일치 여부를 확인합니다.
     *
     * @param itemId           게시글 id
     * @param commentId        답글 id
     * @param writer    답글 작성자
     * @param password  비밀번호
     * @return  CommentEntity        입력받은 writer 와 password 를 게시글 id와 비교 후 일치 한다면 salesItemEntity 를 반환
     * @throws IllegalAccessException   검증 실패 시 반환
     * @since 2023-07-04 수정
     */
    public CommentEntity checkUser(Integer itemId, Integer commentId, String writer, String password) throws IllegalAccessException {
        CommentEntity commentEntity = checkCommentId(itemId, commentId);
        if( !commentEntity.getWriter().equals(writer) || !commentEntity.getPassword().equals(password)){
            throw new IllegalAccessException("허용되지 않는 접근자 입니다.");
        }
        return commentEntity;
    }
}
