package kyungmin.tcpip.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private int no;
  private String name;
  private int price;
  private int stock;

  @Override
  public String toString() {
    //System.out.println("no\t\tname\t\t\t\t\t\t\t\tprice\t\t\t\tstock");
    return getNo() + "\t\t" + getName() + "\t\t\t\t\t\t\t\t" + getPrice() + "\t\t\t" + getStock();
  }
}
