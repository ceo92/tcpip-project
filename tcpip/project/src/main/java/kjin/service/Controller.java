package kjin.service;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Controller {

    Scanner sc = new Scanner(System.in);
    List<Map<String, Object>> ProductList = new ArrayList<>();

    Gson gson = new Gson();

    public void getProductList(){

        System.out.println("---------------------------------------------------------");
        System.out.println("no       name                     price         stock    ");
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < ProductList.size(); i++) {
            System.out.print(i+1 + "       ");
            Map<String, Object> product = ProductList.get(i);
            System.out.print(product.get("name") + "                ");
            System.out.print(product.get("price") + "         ");
            System.out.print(product.get("stock") + "    ");
            System.out.println();
            }
        // json 형식으로 변환되는지 출력문
        System.out.println(gson.toJson(ProductList));
        System.out.println("---------------------------------------------------------");
        System.out.println("메뉴 : 1. Create | 2. Update | 3. Delete | 4. Exit");
        System.out.print("선택 : ");
    }

    public void getMenu(int num){
        switch (num){
            case 1:
                productCreate();
                break;
            case 2:
                productUpdate();
                break;
            case 3:
                productDelete();
                break;
            case 4:
                exit();
                break;
            default:
                System.out.println("없는 메뉴 입니다. 다시 입력해 주세요");
                break;
        }
    }

    public void productCreate() {
        System.out.println("[ 상품 생성 ]");
        Map<String, Object> product = new LinkedHashMap<>();
        System.out.print("상품 이름 : ");
        product.put("name", sc.next());
        System.out.print("상품 가격 : ");
        product.put("price", sc.next());
        System.out.print("상품 재고 : ");
        product.put("stock", sc.next());

        ProductList.add(product);
    }

    public void productUpdate() {
        System.out.println("[ 상품 수정 ]");
        System.out.print("상품 번호 : ");
        int num = sc.nextInt() - 1;

        if (num >= 0 && num < ProductList.size()) {
            Map<String, Object> product = ProductList.get(num);
            System.out.print("이름 변경 : ");
            product.put("name", sc.next());
            System.out.print("가격 변경 : ");
            product.put("price", sc.next());
            System.out.print("재고 변경 : ");
            product.put("stock", sc.next());
        } else {
            System.out.println("잘못된 상품 번호입니다. 다시 입력해 주세요.");
        }
    }

    public void productDelete() {
        System.out.println("[ 상품 삭제 ]");
        System.out.print("상품 번호 : ");
        int num = sc.nextInt() - 1;

        if (num >= 0 && num < ProductList.size()) {
            ProductList.remove(num);
            System.out.println("상품이 삭제되었습니다.");
        } else {
            System.out.println("잘못된 상품 번호입니다. 다시 입력해 주세요.");
        }
    }

    public void exit(){
        System.out.println("프로그램 종료");
        System.exit(0);
    }

}