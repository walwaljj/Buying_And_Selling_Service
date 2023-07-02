package likelion.market.service;

import likelion.market.dto.ResponseMessageDto;
import likelion.market.dto.ResponseSalesItemPageDto;
import likelion.market.dto.SalesItemDto;
import likelion.market.entity.SalesItemEntity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesItemService {
    private final SalesItemRepository salesItemRepository;

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

        ResponseMessageDto response = new ResponseMessageDto();
        response.setMessage("등록이 완료되었습니다.");
        return response;
    }

    /**
     * 상품을 전체 조회합니다 (페이징)
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
     * @param id        게시글 ID
     * @return SalesItemDto 조회 성공 시 반환
     * @throws ResponseStatusException  상품 게시글이 존재하지 않을 경우 예외 던짐
     * @since 2023-06-29
     */

    public SalesItemDto readSalesItemById(Integer id){
        Optional<SalesItemEntity> optionalEntity = salesItemRepository.findById(id);
        if(optionalEntity.isPresent()){
            return SalesItemDto.fromEntity(optionalEntity.get());
        }
        // 게시글 id 가 존재하지 않을 때 예외를 던짐
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    /**
     * 상품을 삭제합니다.
     *
     * @param id       게시글의 ID
     * @param writer   게시글 작성자
     * @param password 비밀번호
     * @return ResponseMessageDto#getMessage() 성공 메세지 반환
     * @throws IllegalAccessException 삭제 권한이 없을 경우 예외 던짐
     * @throws ResponseStatusException  상품 게시글이 존재하지 않을 경우 예외 던짐
     * @since 2023-07-01
     */
    public ResponseMessageDto deleteSalesItem(Integer id, String writer, String password ) throws IllegalAccessException, IOException {

        // 게시글 조회
        Optional<SalesItemEntity> optionalSalesItem = salesItemRepository.findById(id);
        ResponseMessageDto response = new ResponseMessageDto();

        if(optionalSalesItem.isPresent()){

            SalesItemEntity entity = optionalSalesItem.get();
            log.info("Path = {}",String.format("item/%d",id));

            if(entity.getWriter().equals(writer) && entity.getPassword().equals(password)){
                salesItemRepository.deleteById(id);

                // 등록했던 상품 이미지 삭제
                FileUtils.deleteDirectory(new File(String.format("item/%d",id)));
                response.setMessage("물품을 삭제했습니다.");

                return response;
            }else{
                // 아이디 또는 비밀번호가 일치하지 않을 때
                throw new IllegalAccessException("허용되지 않는 접근자 입니다.");
            }
        }
        else{
            // 게시글 id 가 존재하지 않을 때 예외를 던짐
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * 상품 정보를 업데이트 합니다.
     * @param id      게시글 id
     * @param dto     상품 정보
     * @return ResponseMessageDto#getMessage() 성공 메세지 반환
     * @throws IllegalAccessException 삭제 권한이 없을 경우 예외 던짐
     * @throws IllegalAccessException 상품 게시글이 존재하지 않을 경우 예외 던짐
     * @since 2023-06-29
     */
    public ResponseMessageDto updateSalesItem(Integer id, SalesItemDto dto) throws IllegalAccessException {
        Optional<SalesItemEntity> optionalSalesItem = salesItemRepository.findById(id);

        ResponseMessageDto response = new ResponseMessageDto();

        if(!optionalSalesItem.isPresent()){
            // 게시글 id 가 존재하지 않을 때 예외를 던짐
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // update 할 id 존재여부 확인
        else{
            SalesItemEntity entity = optionalSalesItem.get();
            // 아이디 , 비밀번호 확인
            if(entity.getWriter().equals(dto.getWriter()) &&
                    entity.getPassword().equals(dto.getPassword()) ){

                entity.setTitle(dto.getTitle());
                entity.setDescription(dto.getDescription());
                entity.setMinPriceWanted(dto.getMinPriceWanted());
                SalesItemDto.fromEntity(salesItemRepository.save(entity));

                response.setMessage("물품이 수정되었습니다.");
                return response;
            }
            // 아이디 또는 비밀번호가 일치하지 않을 때
            else{
                throw new IllegalAccessException("허용되지 않는 접근자 입니다.");
            }

        }
    }


    /**
     * 상품 이미지를 업로드합니다
     *
     * @param id        게시글 id
     * @param writer    게시글 작성자
     * @param password  비밀번호
     * @param image         상품 이미지
     * @return ResponseMessageDto#getMessage() 성공 메세지 반환
     * @throws IllegalAccessException 삭제 권한이 없을 경우 예외 던짐
     * @throws ResponseStatusException 상품 게시글이 존재하지 않을 경우 예외 던짐
     * @throws IllegalArgumentException 상품 게시글이 존재하지 않을 경우 예외 던짐
     * @since 2023-07-01
     */
    public ResponseMessageDto updateImage(Integer id, String writer, String password ,MultipartFile image) throws IOException, IllegalAccessException {

        // 유저 찾기
        Optional<SalesItemEntity> optionalSalesItem = salesItemRepository.findById(id);

        if(image.isEmpty()){
            // 이미지 파일이 없을 때 예외를 던짐
            throw new IllegalArgumentException("이미지 파일을 첨부해주세요");
        }

        if(!optionalSalesItem.isPresent()){
            // 게시글 id 가 존재하지 않을 때 예외를 던짐
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else{
            SalesItemEntity entity = optionalSalesItem.get();
            if(entity.getWriter().equals(writer) && entity.getPassword().equals(password)){

                // id 로 된 폴더 생성
                Files.createDirectories(Path.of(String.format("item/%d/",id)));

                // 확장자 분리
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

                ResponseMessageDto ResponseMessageDto = new ResponseMessageDto();
                ResponseMessageDto.setMessage("이미지가 등록되었습니다.");
                return ResponseMessageDto;
            }else{
                throw new IllegalAccessException("허용되지 않는 접근자 입니다.");
            }

        }



    }








}
