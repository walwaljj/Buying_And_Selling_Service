/**
 * @project MiniProject_Basic_JungSyHyeon
 */


package likelion.market.contoller;

import likelion.market.dto.NegotiationDto;
import likelion.market.dto.ResponseMessageDto;
import likelion.market.dto.ResponseNegotiationPageDto;
import likelion.market.service.NegotiationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * NegotiationController class
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class NegotiationController {
    private final NegotiationService negotiationService;

    /**
     * 게시글에 제안 등록
     *
     */
    /** 구매 제안 등록
     *
     * @param itemId        게시글 id
     * @param dto           요청 정보
     * @return  ResponseMessageDto  결과를 메세지로 반환
     * @since 2023-07-04
     */
    @PostMapping("/{itemId}/proposals")
    public ResponseMessageDto create(@PathVariable Integer itemId, @RequestBody NegotiationDto dto){
        return negotiationService.createNegotiation(itemId, dto);
    }


    /** 구매 제안 조회
     *
     * @param itemId         게시글 id
     * @param writer         조회 요청자
     * @param password        요청자 비밀번호
     * @param pageNumber      페이지 번호
     * @return  Page           결과에 대한 정보를 페이지타입으로 반환
     * @throws IllegalAccessException   인증 실패 시 예외
     * @since 2023-07-04
     */
    @GetMapping("/{itemId}/proposals")
    public Page<ResponseNegotiationPageDto> readNegotiationAll(@PathVariable Integer itemId,
                                                               @RequestParam(value = "writer") String writer,
                                                               @RequestParam(value = "password") String password,
                                                               @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) throws IllegalAccessException {
        return negotiationService.readNegotiationAll(itemId, writer, password,pageNumber);
    }


    /**게시글에 제안 업데이트
     *
     * @param itemId       게시글 id
     * @param proposalId    구매제안 id
     * @param dto           요청 정보
     * @return  ResponseMessageDto  결과를 메세지로 반환
     * @throws IllegalAccessException   인증 실패 시 예외를 던짐
     * @since 2023-07-04
     */
    @PutMapping("/{itemId}/proposals/{proposalId}")
    public ResponseMessageDto updateNegotiation(@PathVariable Integer itemId, @PathVariable Integer proposalId ,
                                                      @RequestBody NegotiationDto dto) throws IllegalAccessException {
        return negotiationService.updateNegotiation(itemId,proposalId, dto);
    }


    /**구매 제안 삭제
     *
     * @param itemId        게시글 id
     * @param proposalId    구매제안 id
     * @param dto           요청 정보
     * @return  ResponseMessageDto  결과를 메세지로 반환
     * @throws IllegalAccessException   인증 실패 시 예외를 던짐
     * @since 2023-07-03
     */
    @DeleteMapping("/{itemId}/proposals/{proposalId}")
    public ResponseMessageDto deleteNegotiation(@PathVariable Integer itemId, @PathVariable Integer proposalId,
                                                @RequestBody  NegotiationDto dto) throws IllegalAccessException {
        return negotiationService.deleteNegotiation(itemId, proposalId, dto);
    }



}
