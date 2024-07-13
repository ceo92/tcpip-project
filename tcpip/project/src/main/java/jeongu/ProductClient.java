package jeongu;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ProductClient {

  private static final String SERVER_IP = "127.0.0.1";
  private static final int SERVER_PORT = 8080;
  static ObjectMapper mapper = new ObjectMapper();
  private static final Scanner scanner = new Scanner(System.in);
  private static final String patternForMenu = "^[1-4]";
  private static final String patternForNum = "^[0-9]*$";
  private static final String patternForName = "^[a-zA-Z가-힣0-9]*$";


  public static void main(String[] args) {
    new ProductClient().start();
  }

  public void start() {
    Socket socket = null;

    try {
      // 서버 연결
      socket = new Socket(SERVER_IP, SERVER_PORT);

      System.out.println("서버에 연결되었습니다.");

      while (true) {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String responseJson = in.readLine();

        Response response = mapper.readValue(responseJson, Response.class);

        String status = response.getStatus();
        if (status.equals("fail")) {
          System.out.println("예기치 못한 오류가 발생했습니다.");
          break;
        }

        ArrayList<Product> arrayList = response.getData();

        productList(arrayList);
        showMenu();

        Product product = new Product();

        int menu;

        String input = scanner.next();
        if (Pattern.matches(patternForMenu, input)) {
          menu = Integer.parseInt(input);
        } else {
          menu = 0;
        }

        if (menu == 1) {
          product = createMenu();
        } else if (menu == 2) {
          product = updateMenu();
        } else if (menu == 3) {
          product = deleteMenu();
        } else if (menu == 4) {
          break;
        } else {
          System.out.println("잘못된 선택입니다. 1 ~ 4의 번호를 입력해주세요.");
        }

        PrintWriter out = new PrintWriter(socket.getOutputStream());

        Request request = new Request(menu, product);
        String requestJson = mapper.writeValueAsString(request);

        out.println(requestJson);
        out.flush();

      }

    } catch (IOException e) {
      System.err.println(e.getMessage());
    } finally {
      try {
        // 소켓 닫기
        if (socket != null) {
          socket.close();
          System.out.println("서버와 연결을 종료합니다.");
        }
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    }

  }

  private static Product createMenu() {
    Product product = new Product();
    while (true) {
      String input;
      System.out.println("[상품 생성]");
      product.setNo(0);

      System.out.print("상품 이름:");
      input = scanner.next();
      if (!Pattern.matches(patternForName, input)) {
        System.out.println("상품 이름은 문자, 숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        System.out.println();
        continue;
      }
      product.setName(input);

      System.out.print("상품 가격:");
      input = scanner.next();
      if (!Pattern.matches(patternForNum, input)) {
        System.out.println("상품 가격은 숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        System.out.println();
        continue;
      }
      product.setPrice(Integer.parseInt(input));

      System.out.print("상품 재고:");
      input = scanner.next();
      if (!Pattern.matches(patternForNum, input)) {
        System.out.println("상품 재고는 숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        System.out.println();
        continue;
      }
      product.setStock(Integer.parseInt(input));
      break;
    }
    return product;
  }

  private static Product updateMenu() {
    Product product = new Product();
    while (true) {
      String input;
      System.out.println("[상품 수정]");
      System.out.print("상품 번호:");
      input = scanner.next();
      if (!Pattern.matches(patternForNum, input)) {
        System.out.println("상품 번호는 숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        System.out.println();
        continue;
      }
      product.setNo(Integer.parseInt(input));

      System.out.print("이름 변경:");
      input = scanner.next();
      if (!Pattern.matches(patternForName, input)) {
        System.out.println("상품 이름은 문자, 숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        System.out.println();
        continue;
      }
      product.setName(input);

      System.out.print("가격 변경:");
      input = scanner.next();
      if (!Pattern.matches(patternForNum, input)) {
        System.out.println("상품 가격은 숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        System.out.println();
        continue;
      }
      product.setPrice(Integer.parseInt(input));

      System.out.print("재고 변경:");
      input = scanner.next();
      if (!Pattern.matches(patternForNum, input)) {
        System.out.println("상품 재고는 숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        System.out.println();
        continue;
      }
      product.setStock(Integer.parseInt(input));
      break;
    }
    return product;
  }

  private static Product deleteMenu() {
    Product product = new Product();
    while (true) {
      String input;
      System.out.println("[상품 수정]");
      System.out.print("상품 번호:");
      input = scanner.next();
      if (!Pattern.matches(patternForNum, input)) {
        System.out.println("상품 번호는 숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        System.out.println();
        continue;
      }
      product.setNo(Integer.parseInt(input));
      break;
    }
    return product;
  }


  private static void productList(ArrayList<Product> products) {
    System.out.println();
    System.out.println("------------------------------------------");
    System.out.println("no\t\tname\t\t\t\t\t\t\tprice\t\t\tstock");
    System.out.println("------------------------------------------");
    for (Product product : products) {
      System.out.printf("%-4d\t%-16s\t%-9d\t%d\n", product.getNo(), product.getName(),
          product.getPrice(), product.getStock());
    }
    System.out.println("------------------------------------------");
  }

  private static void showMenu() {
    System.out.println("메뉴: 1. Creat | 2. Update | 3. Delete | 4. Exit");
    System.out.print("선택: ");
  }


}
