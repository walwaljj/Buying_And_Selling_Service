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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class SalesItemController {

    private final SalesItemService salesItemService;

    /**
     * 상품 등록
     * @since 2023-06-29
     */
    @PostMapping
    public ResponseMessageDto createSaleItem(@RequestBody SalesItemDto dto){
        return salesItemService.createSalesItem(dto);
    }

    /**
     * 상품 전체 조회
     * @since 2023-07-02 반환 타입을 Page 로 변경
     */
    @GetMapping()
    public Page<ResponseSalesItemPageDto> readSaleItemAll(@RequestParam (value = "page", defaultValue = "0") int pageNumber,
                                                          @RequestParam (value = "limit", defaultValue = "25") int pageSize){
        return salesItemService.readSalesItemAll(pageNumber,pageSize);
    }

    /**
     * 상품 조회
     * @since 2023-06-29
     */
    @GetMapping("/{id}")
    public SalesItemDto readSaleItem(@PathVariable Integer id){
        return salesItemService.readSalesItemById(id);
    }

    /**
     * 상품 정보 업데이트
     * @since 2023-06-29
     */
    @PutMapping("/{id}")
    public ResponseMessageDto updateSaleItem(@PathVariable Integer id,  @RequestBody SalesItemDto dto) throws IllegalAccessException {
        return salesItemService.updateSalesItem(id,dto);
    }

    /**
     * 상품 이미지 업데이트
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
     * @since 2023-06-30
     */
    @DeleteMapping("/{id}")
    public ResponseMessageDto deleteSaleItem(@PathVariable Integer id,
                                      @RequestBody SalesItemDto dto) throws IllegalAccessException, IOException {
        return salesItemService.deleteSalesItem(id,dto.getWriter(),dto.getPassword());
    }


}
