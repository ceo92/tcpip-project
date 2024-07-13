package kyungmin.tcpip.project.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import kyungmin.tcpip.project.server.ProductService;
import kyungmin.tcpip.project.domain.Product;
import kyungmin.tcpip.project.dto.ProductClientDto;
import kyungmin.tcpip.project.dto.ProductServerDto;

public class ProductClient {


  public static final ObjectMapper objectMapper = new ObjectMapper();

  private static final ProductService productService = new ProductService();

  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  private static int sequence = 0;

  public static void main(String[] args) throws IOException {

    int a=0;
    while (true){
      if (a++==0) {
        System.out.println("-".repeat(50));
        System.out.println("no\t\tname\t\t\t\t\t\t\t\tprice\t\t\t\tstock");
        System.out.println("-".repeat(50));
        System.out.println("-".repeat(50));
        System.out.println("메뉴: 1. Create | 2. Update | 3.Delete | 4.Exit");
        System.out.print("선택 : ");
      }
      int menuNum = Integer.parseInt(br.readLine());
      switch (menuNum){
        case 1:
          doSave(menuNum);
          break;

        case 2:
          doUpdate(menuNum);
          break;

        case 3:
          doDelete(menuNum);
          break;
        case 4:
          System.out.println("시스템을 종료합니다.");
          return;
      }
    }
  }

  /**
   * 클라이언트 => 서버
   */
  private static void doDelete(int menuNum) throws IOException {
    System.out.println("[상품 삭제]");
    System.out.print("상품 번호 : ");
    int deleteNo = Integer.parseInt(br.readLine());
    ProductClientDto deleteProductClientDto = new ProductClientDto(menuNum, deleteNo);
    String deleteJson = objectMapper.writeValueAsString(deleteProductClientDto);
    productService.deleteProduct(deleteJson);
  }

  private static void doSave(int menuNum) throws IOException {
    System.out.println("[상품 생성]");
    System.out.print("상품 이름: ");
    String productName = br.readLine();
    System.out.print("상품 가격: ");
    int price = Integer.parseInt(br.readLine());
    System.out.print("상품 재고: ");
    int stockQuantity = Integer.parseInt(br.readLine());
    ProductClientDto saveProductClientDto = new ProductClientDto(menuNum, ++sequence , productName , price , stockQuantity);
    String saveJson = objectMapper.writeValueAsString(saveProductClientDto);
    productService.saveProduct(saveJson);
  }

  private static void doUpdate(int menuNum) throws IOException {
    System.out.println("[상품 수정]");
    System.out.print("상품 번호: ");
    int updateNo = Integer.parseInt(br.readLine());
    System.out.print("이름 변경 : ");
    String updateName = br.readLine();
    System.out.print("가격 변경 : ");
    int updatePrice = Integer.parseInt(br.readLine());
    System.out.print("재고 변경 : ");
    int updateStock = Integer.parseInt(br.readLine());
    ProductClientDto updateProductClientDto = new ProductClientDto(menuNum, updateNo , updateName , updatePrice , updateStock);
    String updateJson = objectMapper.writeValueAsString(updateProductClientDto);
    productService.updateProduct(updateJson);

  }

  /**
   * 서버 => 클라이언트
   */
  public void findProductsAndPrintMenu(String json) throws JsonProcessingException {
    ProductServerDto productServerDto = objectMapper.readValue(json, ProductServerDto.class);
    String status = productServerDto.getStatus();
    Product[] products = productServerDto.getProducts();

    if (status.equals("success")){
      System.out.println("-".repeat(50));
      System.out.println("no\t\tname\t\t\t\t\t\t\t\tprice\t\t\t\tstock");
      System.out.println("-".repeat(50));

      for (Product product : products) {
        System.out.println(product);
      }
      System.out.println("-".repeat(50));
      System.out.println("메뉴: 1. Create | 2. Update | 3.Delete | 4.Exit");
      System.out.print("선택 : ");

    }
    else{
      System.out.println("=".repeat(20)+"Warning!!!!"+"=".repeat(20));
      System.out.println("\t\t\t\t status가 fail로 종료합니다....\t\t\t\t");
      System.out.println("=".repeat(50));
      System.out.println();
    }

  }


}
