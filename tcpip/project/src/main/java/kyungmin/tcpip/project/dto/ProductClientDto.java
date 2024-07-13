package kyungmin.tcpip.project.dto;


/**
 * 클라이언트 => 서버 DTO
 */
public class ProductClientDto {

  private int menuNum;
  private int no;
  private String name;
  private int price;
  private int stock;

  /**
   * 저장 , 수정용 DTO 생성자
   */
  public ProductClientDto(int menuNum, int no, String name, int price, int stock) {
    this.menuNum = menuNum;
    this.no = no;
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

  /**
   * 삭제용 DTO 생성자
   */
  public ProductClientDto(int menuNum , int no){
    this.menuNum = menuNum;
    this.no = no;
  }
  public ProductClientDto() {
  }

  public int getMenuNum() {
    return menuNum;
  }

  public void setMenuNum(int menuNum) {
    this.menuNum = menuNum;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }
}
