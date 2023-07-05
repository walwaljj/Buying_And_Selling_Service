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

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class NegotiationController {
    private final NegotiationService negotiationService;

    /**
     * 게시글에 제안 등록
     * @since 2023-07-04
     */
    @PostMapping("/{itemId}/proposals")
    public ResponseMessageDto create(@PathVariable Integer itemId, @RequestBody NegotiationDto dto){
        return negotiationService.createNegotiation(itemId, dto);
    }

    /**
     * 게시글에 제안 조회
     * @since 2023-07-04
     */
    @GetMapping("/{itemId}/proposals")
    public Page<ResponseNegotiationPageDto> readNegotiationAll(@PathVariable Integer itemId,
                                                               @RequestParam(value = "writer") String writer,
                                                               @RequestParam(value = "password") String password,
                                                               @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) throws IllegalAccessException {
        return negotiationService.readNegotiationAll(itemId, writer, password,pageNumber);
    }

    /**
     * 게시글에 제안 업데이트
     * @since 2023-07-04
     */
    @PutMapping("/{itemId}/proposals/{proposalId}")
    public ResponseMessageDto updateNegotiation(@PathVariable Integer itemId, @PathVariable Integer proposalId ,
                                                      @RequestBody NegotiationDto dto) throws IllegalAccessException {
        return negotiationService.updateNegotiation(itemId,proposalId, dto);
    }

    /**
     * 제안 삭제
     * @since 2023-07-03
     */
    @DeleteMapping("/{itemId}/proposals/{proposalId}")
    public ResponseMessageDto deleteNegotiation(@PathVariable Integer itemId, @PathVariable Integer proposalId,
                                                @RequestBody  NegotiationDto dto) throws IllegalAccessException {
        return negotiationService.deleteNegotiation(itemId, proposalId, dto);
    }



}
