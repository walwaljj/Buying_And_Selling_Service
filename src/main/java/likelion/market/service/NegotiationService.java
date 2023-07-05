/**
 * @project MiniProject_Basic_JungSyHyeon
 * @since 2023-07-04
 */

package likelion.market.service;

import likelion.market.dto.NegotiationDto;
import likelion.market.dto.ResponseMessageDto;
import likelion.market.dto.ResponseNegotiationPageDto;
import likelion.market.entity.NegotiationEntity;
import likelion.market.entity.SalesItemEntity;
import likelion.market.repository.NegotiationRepository;
import likelion.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class NegotiationService {
    private final NegotiationRepository negotiationRepository;
    private final SalesItemRepository salesItemRepository;

    /** 구매 제안을 등록합니다.
     *
     * @param itemId    게시글 id
     * @param dto       구메 제안 정보
     * @return          ResponseMessageDto성공 메세지 반환
     * @since 2023-07-04
     */
    public ResponseMessageDto createNegotiation(Integer itemId, NegotiationDto dto) {
        NegotiationEntity negotiationEntity = new NegotiationEntity();
        negotiationEntity.setItemId(checkItemId(itemId));
        negotiationEntity.setWriter(dto.getWriter());
        negotiationEntity.setPassword(dto.getPassword());
        negotiationEntity.setSuggestedPrice(dto.getSuggestedPrice());

        negotiationRepository.save(negotiationEntity);
        return getResponseMessageDto( "구매 제안이 등록되었습니다.");
    }

    /**구매 제안을 조회합니다.
     *
     * @param itemId    게시글 Id
     * @param writer    작성자 (구매 제한자 또는 게시글 작성자)
     * @param password  비밀번호
     * @param pageNumber    페이지
     * @return  Page<ResponseNegotiationPageDto> 작성자에 따라 보여줄 내용을 구분하고 , Page에 담아 반환
     * @throws IllegalAccessException id, password 검증 실패 시 반환
     * @since 2023-07-04
     */
    public Page<ResponseNegotiationPageDto> readNegotiationAll(Integer itemId, String writer, String password, int pageNumber) throws IllegalAccessException {

        SalesItemEntity salesItemEntity = checkItemId(itemId);
        Pageable pageable = PageRequest.of(pageNumber, 25);
        List<NegotiationEntity> negotiation = salesItemEntity.getNegotiation();
        List<NegotiationEntity> negotiationEntities = negotiation.stream().filter(entity -> entity.getWriter().equals(writer)).toList();

        // 판매자일때
        if( salesItemEntity.getWriter().equals(writer) && salesItemEntity.getPassword().equals(password) ){
            return new PageImpl<>(negotiation, pageable, negotiation.size()).map(ResponseNegotiationPageDto::fromEntity);
        }

        //구매 제안자일때
        if(!negotiationEntities.isEmpty()){
            List<NegotiationEntity> negotiationUser = negotiationEntities.stream().filter(entity -> entity.getPassword().equals(password)).toList();
            if(!negotiationUser.isEmpty()) {
                return new PageImpl<>(negotiationUser, pageable, negotiation.size()).map(ResponseNegotiationPageDto::fromEntity);
            }
        }

        throw new IllegalAccessException("허용되지 않는 접근자 입니다.");
    }

    /** 구매제안자가 구매 제안 금액을 업데이트 합니다.
     *
     * @param negotiationUser   구매 제안자
     * @param dto               금액 변경 정보
     * @return  ResponseMessageDto 성공 메세지 반환
     * @throws IllegalAccessException   id, password 검증 실패 시 반환
     * @since 2023-07-04
     */
    private ResponseMessageDto updateSuggestedPrice( NegotiationEntity negotiationUser , NegotiationDto dto) throws IllegalAccessException {
        // 요청한 금액으로 구매제안 금액 수정
        negotiationUser.setSuggestedPrice(dto.getSuggestedPrice());
        negotiationRepository.save(negotiationUser);

        return getResponseMessageDto( "구매 제안이 수정되었습니다.");
    }


    /**판매자가 구매 제안 상태를 업데이트 합니다. ("거절" || "수락" )
     *
     * @param itemId            게시글 Id
     * @param proposalId        구매 제안글 Id
     * @param dto               변경 정보 , 요청자 아이디 , 비밀번호
     * @return ResponseMessageDto 성공 메세지 반환
     * @throws IllegalAccessException   id, password 검증 실패 시 반환
     * @since 2023-07-04
     */
    private ResponseMessageDto updateStatusBySeller(Integer itemId, Integer proposalId, NegotiationDto dto) throws IllegalAccessException {
        // user 와 구매 제안글 id 확인
        SalesItemEntity salesItemUser = checkSalesItemUser(itemId, dto.getWriter(), dto.getPassword());
        NegotiationEntity negotiationEntity = checkNegotiationId(salesItemUser.getId(), proposalId);

        // 요청대로 상태 변경  -> "거절" || "수락"
        negotiationEntity.setStatus(dto.getStatus());
        negotiationRepository.save(negotiationEntity);
        return getResponseMessageDto( "제안의 상태가 변경되었습니다.");
    }

    /**구매제안자가 구매 제안 상태를 업데이트 합니다. ("확정")
     *
     * @param proposalId        구매 제안글 Id
     * @param dto               구매 제안글 정보
     * @param negotiationUser   구매 제안자 정보
     * @return  ResponseMessageDto 성공 메세지 반환
     * @since 2023-07-04
     */
    private ResponseMessageDto updateStatusByBuyer(Integer proposalId, NegotiationDto dto, NegotiationEntity negotiationUser) {

        negotiationUser.setStatus(dto.getStatus());
        negotiationRepository.save(negotiationUser);

        // 구매 확정 시 상태변경
        statusChangeSoldOut(proposalId, negotiationUser);
        return getResponseMessageDto("구매가 확정되었습니다.");
    }


    /**확정시 게시글과 구매제안의 상태를 업데이트 합니다. ( 게시글 : "판매 완료", 구매제안 : "거절" (확정 제외))
     *
     * @param proposalId       구매 제안글 Id
     * @param negotiationUser  구매 제안자 정보
     *
     * @since 2023-07-04
     */
    private void statusChangeSoldOut(Integer proposalId, NegotiationEntity negotiationUser) {

        // 대상 물품의 상태는 판매 완료
        SalesItemEntity itemId = negotiationUser.getItemId();
        SalesItemEntity salesItemEntity = salesItemRepository.findById(itemId.getId()).get();
        salesItemEntity.setStatus("판매 완료");
        salesItemRepository.save(salesItemEntity);

        // 확정 시 다른 nego 모두 거절
        salesItemEntity.getNegotiation().stream().filter(entity -> !entity.getId().equals(proposalId))
                .map(entity -> { entity.setStatus("거절");
                    return entity;
                })
                .forEach(negotiationRepository::save);
    }

    /** 구매제안 상태와 금액을 업데이트 합니다.
     *
     * @param itemId         게시글 Id
     * @param proposalId    구매 제안글 Id
     * @param dto           구매제안 변경 정보
     * @return ResponseMessageDto   성공 메세지 반환
     * @throws IllegalAccessException   id, password 검증 실패 시 반환
     * @since 2023-07-04
     */
    public ResponseMessageDto updateNegotiation(Integer itemId, Integer proposalId, NegotiationDto dto) throws IllegalAccessException {

        // 구매자는 가격 제안 수정 가능
        if(dto.getSuggestedPrice() != null){
            NegotiationEntity negotiationUser = checkNegotiationUser(itemId, proposalId, dto.getWriter(), dto.getPassword());
            return updateSuggestedPrice(negotiationUser , dto);
        }

        // 구매자는 수락상태 일때 확정 가능

        if( dto.getStatus().equals("확정")){
            NegotiationEntity negotiationUser = checkNegotiationUser(itemId, proposalId, dto.getWriter(), dto.getPassword());
            if(negotiationUser.getStatus().equals("수락"))
            return updateStatusByBuyer(proposalId, dto, negotiationUser);
        }

        // 판매자는 수락 또는 거절 처리 가능
        if( (dto.getStatus().equals("수락") || dto.getStatus().equals("거절")) ){
            return updateStatusBySeller(itemId , proposalId, dto);

        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "변경 가능한 상태가 아닙니다.");

    }

    /** 구매제안을 삭제합니다.
     *
     * @param itemId        게시글 Id
     * @param proposalId    구매 제안글 Id
     * @param dto           삭제 요청자 정보
     * @return ResponseMessageDto           성공 메세지 반환
     * @throws IllegalAccessException      id, password 검증 실패 시 반환
     * @since 2023-07-04
     */
    public ResponseMessageDto deleteNegotiation(Integer itemId, Integer proposalId, NegotiationDto dto) throws IllegalAccessException {

        NegotiationEntity negotiationEntity = checkNegotiationUser(itemId, proposalId, dto.getWriter(), dto.getPassword());

        negotiationRepository.delete(negotiationEntity);

        return getResponseMessageDto( "구매 제안이 삭제되었습니다.");
    }


    /**
     * 결과에 대한 응답 메세지를 반환합니다.
     *
     * @param msg
     * @return ResponseMessageDto
     */
    private ResponseMessageDto getResponseMessageDto(String msg) {
        ResponseMessageDto response = new ResponseMessageDto();
        response.setMessage(msg);
        return response;
    }

    /**게시글 존재 여부를 확인합니다
     *
     * @param itemId    item Id
     * @throws ResponseStatusException   검증 실패 시 http 404 반환
     * @return  SalesItemEntity     게시글이 존재한다면 SalesItemEntity 반환
     * @since 2023-07-04
     */
    public SalesItemEntity checkItemId(Integer itemId){
        return salesItemRepository.findById(itemId)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    /**구매 제안글 존재 여부를 확인합니다
     *
     * @param itemId    item Id
     * @param negotiationId     구매제안글 Id
     * @throws ResponseStatusException   검증 실패 시 http 404 반환
     * @return  NegotiationEntity  구매 제안이 존재한다면 NegotiationEntity 반환
     * @since 2023-07-04
     */
    private NegotiationEntity checkNegotiationId(Integer itemId, Integer negotiationId){
        SalesItemEntity salesItemEntity = checkItemId(itemId);
        List<NegotiationEntity> negotiationEntity = salesItemEntity.getNegotiation();
        return negotiationEntity.stream().filter( entity -> entity.getId().equals(negotiationId))
                .findFirst().orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"checkNegotiationId");
                });
    }

    /**구매 제안자를 검증합니다.
     *
     * @param itemId            item Id
     * @param negotiationId    구매제안글 Id
     * @param writer           요청자 아이디
     * @param password         요청자 비밀번호
     * @throws IllegalAccessException   검증 실패 시 http 404 반환
     * @return  NegotiationEntity      구매 제안자가 맞다면 NegotiationEntity 반환
     * @since 2023-07-04
     */
    private NegotiationEntity checkNegotiationUser(Integer itemId, Integer negotiationId, String writer, String password) throws IllegalAccessException {
        NegotiationEntity negotiationEntity = checkNegotiationId(itemId, negotiationId);
        if( !negotiationEntity.getWriter().equals(writer) || !negotiationEntity.getPassword().equals(password)){
            throw new IllegalAccessException("허용되지 않는 접근자 입니다.");
        }
        return negotiationEntity;
    }

    /**게시글 일치 여부를 확인합니다.
     *
     * @param itemId     게시글 id
     * @param writer    답글 작성자
     * @param password  비밀번호
     * @throws IllegalAccessException   검증 실패 시 반환
     * @return SalesItemEntity      게시글 작성자가 맞다면 SalesItemEntity 반환
     * @since 2023-07-04
     */
    private SalesItemEntity checkSalesItemUser(Integer itemId, String writer, String password) throws IllegalAccessException {
        SalesItemEntity salesItemEntity = checkItemId(itemId);
        if( !salesItemEntity.getWriter().equals(writer) || !salesItemEntity.getPassword().equals(password)){
            throw new IllegalAccessException("허용되지 않는 접근자 입니다.");
        }
        return salesItemEntity;
    }

}
