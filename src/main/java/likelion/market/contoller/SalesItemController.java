/**
 * @project MiniProject_Basic_JungSyHyeon
 */

package likelion.market.contoller;


import likelion.market.dto.ResponseMessageDto;
import likelion.market.dto.ResponseSalesItemPageDto;
import likelion.market.dto.SalesItemDto;
import likelion.market.service.SalesItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * SalesItemController class
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class SalesItemController {

    private final SalesItemService salesItemService;


    /**
     * 상품 등록
     * @param dto   요청 정보
     * @return ResponseMessageDto 결과를 메세지로 반환
     * @since 2023-06-29
     */
    @PostMapping
    public ResponseMessageDto createSaleItem(@RequestBody SalesItemDto dto){
        return salesItemService.createSalesItem(dto);
    }


    /**
     * 상품 전체 조회
     * @param pageNumber    페이지 수
     * @param pageSize      페이지에 담을 게시글 수
     * @return  Page        결과를 페이지 타입으로 반환
     * @since 2023-07-02
     */
    @GetMapping()
    public Page<ResponseSalesItemPageDto> readSaleItemAll(@RequestParam (value = "page", defaultValue = "0") int pageNumber,
                                                          @RequestParam (value = "limit", defaultValue = "25") int pageSize){
        return salesItemService.readSalesItemAll(pageNumber,pageSize);
    }



    /**
     * 상품 조회
     * @param id        게시글 id
     * @return   SalesItemDto   결과를 dto로 변환해 반환
     * @since 2023-06-29
     */
    @GetMapping("/{id}")
    public SalesItemDto readSaleItem(@PathVariable Integer id){
        return salesItemService.readSalesItemById(id);
    }


    /**
     * 상품 정보 업데이트
     * @param id        게시글 Id
     * @param dto       요청 정보
     * @return  ResponseMessageDto      결과를 메세지에 담아 반환
     * @throws IllegalAccessException   인증 실패시 예외
     * @since 2023-06-29
     */
    @PutMapping("/{id}")
    public ResponseMessageDto updateSaleItem(@PathVariable Integer id,  @RequestBody SalesItemDto dto) throws IllegalAccessException {
        return salesItemService.updateSalesItem(id,dto);
    }


    /**
     * 상품 이미지 업데이트
     * @param id          게시글 Id
     * @param writer     요청자 아이디
     * @param password  요청자 비밀번호
     * @param image     이미지 파일
     * @return  ResponseMessageDto  결과를 메세지로 반환
     * @throws IOException      이미지 파일 업로드 및 폴더 생성시 예외
     * @throws IllegalAccessException   이미지 파일이 존재하지 않을 경우 예외 던짐
     * @since 2023-06-30
     */
    @PutMapping(value = "/{id}/image" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseMessageDto updateSaleItemImage(@PathVariable Integer id,
                                           String writer, String password,
                                           @RequestBody MultipartFile image) throws IOException, IllegalAccessException {

        return salesItemService.updateImage(id, writer, password , image);
    }


    /**
     * 상품 삭제
     * @param id        게시글 id
     * @param dto       요청 정보
     * @return  ResponseMessageDto  결과를 메세지로 반환
     * @throws IllegalAccessException   인증 실패시 예외
     * @throws IOException      이미지 파일 및 폴더 삭제시 예외
     * @since 2023-06-30
     */
    @DeleteMapping("/{id}")
    public ResponseMessageDto deleteSaleItem(@PathVariable Integer id,
                                      @RequestBody SalesItemDto dto) throws IllegalAccessException, IOException {
        return salesItemService.deleteSalesItem(id,dto.getWriter(),dto.getPassword());
    }


}
