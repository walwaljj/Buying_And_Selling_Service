package likelion.market.service;

import likelion.market.dto.ResponseMessageDto;
import likelion.market.dto.ResponseSalesItemPageDto;
import likelion.market.dto.SalesItemDto;
import likelion.market.entity.CommentEntity;
import likelion.market.entity.NegotiationEntity;
import likelion.market.entity.SalesItemEntity;
import likelion.market.repository.CommentRepository;
import likelion.market.repository.NegotiationRepository;
import likelion.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesItemService {
    private final SalesItemRepository salesItemRepository;
    private final CommentRepository commentRepository;
    private final NegotiationRepository negotiationRepository;

    /**
     * 상품을 등록합니다.
     *
     * @param dto   상품 정보
     * @return ResponseMessageDto#getMessage() 성공 메세지 반환
     * @since 2023-06-29
     */
    public ResponseMessageDto createSalesItem(SalesItemDto dto){

        SalesItemEntity entity = new SalesItemEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setMinPriceWanted(dto.getMinPriceWanted());
        entity.setStatus("판매중");
        entity.setWriter(dto.getWriter());
        entity.setPassword(dto.getPassword());
        SalesItemDto.fromEntity(salesItemRepository.save(entity));

        return getResponseMessageDto("등록이 완료되었습니다.");
    }

    /**
     * 상품을 전체 조회합니다 (페이징)
     *
     * @param pageNumber        페이지
     * @param pageSize          담을 게시글 수
     * @return Page<ResponseSalesItemPageDto> 상품 게시글을 담아 Page 형태로 반환
     * @since 2023-07-02 페이징 처리 및 imageUrl == null 이라면 표출되지 않게 수정완료
     */
    public Page<ResponseSalesItemPageDto> readSalesItemAll(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SalesItemEntity> entityAll = salesItemRepository.findAll(pageable);
        return entityAll.map(ResponseSalesItemPageDto::fromEntity);
    }

    /**
     * 상품을 조회합니다.
     *
     * @param id                게시글 ID
     * @return SalesItemDto   id 조회 성공 시 Dto로 변환 후 반환
     * @since 2023-07-02 검증 메소드 분리로 인한 수정
     */
    public SalesItemDto readSalesItemById(Integer id){
        return SalesItemDto.fromEntity(checkId(id));
    }

    /**
     * 상품을 삭제합니다.
     *
     * @param id       게시글의 ID
     * @param writer   게시글 작성자
     * @param password 비밀번호
     * @return ResponseMessageDto#getMessage() 성공 메세지 반환
     * @throws IllegalAccessException 삭제 권한이 없을 경우 예외 던짐
     * @throws IOException 상품 이미지 삭제 시 발생 예외 처리 (FileUtils)
     * @since 2023-07-02 검증 메소드 분리로 인한 수정
     */
    public ResponseMessageDto deleteSalesItem(Integer id, String writer, String password ) throws IllegalAccessException, IOException {

        // 게시글 조회 및 유저 검증
        SalesItemEntity entity = checkUser(id, writer, password);

        // 등록했던 상품 이미지 삭제
        FileUtils.deleteDirectory(new File(String.format("item/%d",entity.getId())));

        // 상품 답글 삭제
        List<CommentEntity> comment = entity.getComment();
        for (CommentEntity commentEntity : comment) {
            commentRepository.delete(commentEntity);
        }

        // 구매 제안 삭제
        List<NegotiationEntity> nego = entity.getNegotiation();
        for (NegotiationEntity negotiationEntity : nego) {
            negotiationRepository.delete(negotiationEntity);
        }

        // 게시글 삭제
        salesItemRepository.deleteById(entity.getId());

        return getResponseMessageDto("물품을 삭제했습니다.");
    }

    /**
     * 상품 정보를 업데이트 합니다.
     *
     * @param id      게시글 id
     * @param dto     상품 정보
     * @return ResponseMessageDto#getMessage() 성공 메세지 반환
     * @throws IllegalAccessException 상품 게시글이 없거나 권한이 존재하지 않을 경우 예외 던짐
     * @since 2023-07-02 검증 메소드 분리로 인한 수정
     */
    public ResponseMessageDto updateSalesItem(Integer id, SalesItemDto dto) throws IllegalAccessException {

        // 게시글 조회 및 유저 검증
        SalesItemEntity entity = checkUser(id, dto.getWriter(), dto.getPassword());

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setMinPriceWanted(dto.getMinPriceWanted());
        SalesItemDto.fromEntity(salesItemRepository.save(entity));

        return getResponseMessageDto("물품이 수정되었습니다.");
    }

    /**
     * 상품 이미지를 업로드합니다
     *
     * @param id        게시글 id
     * @param writer    게시글 작성자
     * @param password  비밀번호
     * @param image         상품 이미지
     * @return ResponseMessageDto#getMessage() 성공 메세지 반환
     * @throws IllegalArgumentException 이미지 파일이 존재하지 않을 경우 예외 던짐
     * @throws IOException 파일 생성 시 예외 상황
     * @since 2023-07-02 검증 메소드 분리로 인한 수정
     */
    public ResponseMessageDto updateImage(Integer id, String writer, String password ,MultipartFile image) throws IOException, IllegalAccessException {

        // 게시글 조회 및 유저 검증
        SalesItemEntity entity = checkUser(id, writer, password);

        // 이미지 파일이 없을 때
        if(image.isEmpty()){
            throw new IllegalArgumentException("이미지 파일을 첨부해주세요");
        }

        // id 로 된 폴더 생성
        Files.createDirectories(Path.of(String.format("item/%d/",entity.getId())));

        String originalFilename = image.getOriginalFilename();
        String[] split = originalFilename.split("\\.");
        String extension = split[split.length - 1];

        // 현재 시간을 파일이름으로 저장
        LocalDateTime now = LocalDateTime.now();
        Path uploadTo = Path.of(String.format("item/%d/%s.png",id, now.toString().replace(":","")));
        image.transferTo(uploadTo);

        //상품에 imageUrl을 저장
        entity.setImageUrl(String.valueOf(uploadTo));
        salesItemRepository.save(entity);

        return getResponseMessageDto("이미지가 등록되었습니다.");
    }

    /**
     * 작성자 일치 여부를 확인합니다.
     *
     * @param id        게시글 id
     * @param writer    게시글 작성자
     * @param password  비밀번호
     * @return          입력받은 writer 와 password 를 게시글 id와 비교 후 일치 한다면 salesItemEntity 를 반환
     * @throws IllegalAccessException   검증 실패 시 반환
     */
    public SalesItemEntity checkUser(Integer id, String writer, String password) throws IllegalAccessException {
        SalesItemEntity salesItemEntity = checkId(id);
        if( !salesItemEntity.getWriter().equals(writer) || !salesItemEntity.getPassword().equals(password)){
            throw new IllegalAccessException("허용되지 않는 접근자 입니다.");
        }
        return salesItemEntity;
    }

    /**
     * 게시글 존재 여부를 확인합니다.
     *
     * @param id    게시글 id
     * @return      게시글이 존재한다면 SalesItemEntity 반환
     */
    public SalesItemEntity checkId(Integer id){
        Optional<SalesItemEntity> optionalSalesItem = salesItemRepository.findById(id);
        if(!optionalSalesItem.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return optionalSalesItem.get();
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
}
