package kyungmin.tcpip.project.dto;

import kyungmin.tcpip.project.domain.Product;

/**
 * 서버 => 클라이언트 DTO
 */
public class ProductServerDto {
  String status;
  Product[] products;
  public ProductServerDto(){}
  public ProductServerDto(String status, Product[] products) {
    this.status = status;
    this.products = products;
  }

  public ProductServerDto(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public Product[] getProducts() {
    return products;
  }
}
