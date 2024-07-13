package kyungmin.tcpip.project.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kyungmin.tcpip.project.client.ProductClient;
import kyungmin.tcpip.project.domain.Product;
import kyungmin.tcpip.project.dto.ProductClientDto;
import kyungmin.tcpip.project.dto.ProductServerDto;
import kyungmin.tcpip.project.exception.NoProductException;

public class ProductService {
  private final ObjectMapper objectMapper = ProductClient.objectMapper;
  private final ProductRepository productRepository = new ProductRepository();

  private final ProductClient productClient = new ProductClient();
  //LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);


  //ThreadPoolExecutor executorService = new ThreadPoolExecutor(10, 10, 10, SECONDS, queue);



  /**
   * 저장
   */
  public void saveProduct(String saveJson) throws JsonProcessingException {
    try {

      ProductClientDto productClientDto = objectMapper.readValue(saveJson, ProductClientDto.class); //DTO는 메뉴 넘버 포함 , 엔티티는 미포함
      Product saveProduct = new Product(productClientDto.getNo(), productClientDto.getName(), productClientDto.getPrice(), productClientDto.getStock());
      productRepository.save(saveProduct);
      returnResultToClient("success");
    }catch (Exception e){
      returnResultToClient("fail");
    }
  }


  /**
   * 단 건 조회
   */
  public Product findOne(int no) {
    return productRepository.findByNo(no).orElseThrow(()->new NoProductException("요청한 no 번호는 찾을 수 없습니다"));
  }


  /**
   * 수정
   */
  public void updateProduct(String updateJson) throws JsonProcessingException {
    try {
      ProductClientDto productDto = objectMapper.readValue(updateJson, ProductClientDto.class);
      Product findProduct = findOne(productDto.getNo());
      findProduct.setName(productDto.getName());
      findProduct.setPrice(productDto.getPrice());
      findProduct.setStock(productDto.getStock());
      returnResultToClient("success");
    }catch (Exception e){
      returnResultToClient("fail");
    }
  }

  /**
   * 삭제
   */
  public void deleteProduct(String deleteJson) throws JsonProcessingException {
    try {
      ProductClientDto productDto = objectMapper.readValue(deleteJson, ProductClientDto.class);
      Product findProduct = findOne(productDto.getNo());
      productRepository.delete(findProduct);
      returnResultToClient("success");
    }catch (Exception e){
      returnResultToClient("fail");
    }
  }



  public void returnResultToClient(String status) throws JsonProcessingException {
    ProductServerDto productServerDto = new ProductServerDto(status, findProducts().toArray(new Product[0]));
    String successSaveJson = objectMapper.writeValueAsString(productServerDto);
    productClient.findProductsAndPrintMenu(successSaveJson);

  }
  /**
   * 전체 조회
   */
  public List<Product> findProducts() {
    return productRepository.findAll();
  }



}
