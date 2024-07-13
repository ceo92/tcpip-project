package kyungmin.tcpip.project.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kyungmin.tcpip.project.domain.Product;
import kyungmin.tcpip.project.exception.NoProductException;
import org.springframework.stereotype.Repository;

public class ProductRepository {

  private static final List<Product> store = new ArrayList<>();

  //C
  public void save(Product product){
    store.add(product);
  }

  //R\
  public Optional<Product> findByNo(int no){
    return Optional.ofNullable(store.get(no-1));
  }

  public List<Product> findAll(){
    return store;
  }


  //D
  public void delete(Product product){
    store.remove(product);
  }

}
