package jeongu;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductServer {

  private static final int PORT = 8080;
  private static int productNumber = 0;
  private static ArrayList<Product> productList = new ArrayList();
  private final ObjectMapper mapper = new ObjectMapper();

  public static void main(String[] args) {
    new ProductServer().start();
  }

  public void start() {
    ExecutorService threadPool = Executors.newFixedThreadPool(10);

    try {
      ServerSocket serverSocket = new ServerSocket(PORT);
      System.out.println("서버가 가동되었습니다. 포트번호 : " + PORT);

      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("클라이언트가 연결되었습니다. 포트번호 : " + socket.getLocalPort() + ", IP주소 : " + socket.getLocalAddress());
        threadPool.execute(new SocketClient(socket));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  class SocketClient implements Runnable {

    private Socket socket;
    private String status;

    public SocketClient(Socket socket) {
      this.socket = socket;
      this.status = "success";
    }

    @Override
    public void run() {
      try {

        while (true) {

          // 클라이언트에게 리스트 보내기

          PrintWriter out = new PrintWriter(socket.getOutputStream());
          Response response = new Response(status, productList);
          String responseJson = mapper.writeValueAsString(response);

          out.println(responseJson);
          out.flush();

          System.out.println("포트번호 : " + socket.getPort() + ", IP주소 : " + socket.getLocalAddress() + "에게 데이터를 보냈습니다.");

          // 클라이언트 요청 확인
          System.out.println("요청을 기다리는 중...");
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          String requestJson = in.readLine();

          Request request = mapper.readValue(requestJson, Request.class);

          int selectedMenu = request.getMenu();
          Product receivedProduct = request.getData();
          System.out.println("포트번호 : " + socket.getPort() + ", IP주소 : " + socket.getLocalAddress() + "의 요청이 들어왔습니다.");

          if (selectedMenu == 1){
            saveProduct(receivedProduct);
            status = "success";
          } else if (selectedMenu == 2) {
            status = updateProduct(receivedProduct);
          } else if (selectedMenu == 3) {
            status = deleteProduct(receivedProduct);
          } else if (selectedMenu == 4) {
            System.out.println("클라이언트가 연결을 끊었습니다.");
            break;
          } else {
            System.out.println("클라이언트가 잘못된 요청을 하였습니다.");
            status = "fail";
            continue;
          }

          System.out.println(productList);

        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private static void saveProduct(Product product) {
      productNumber++;
      product.setNo(productNumber);
      productList.add(product);
    }

    private static String updateProduct(Product product) {
      for (Product target : productList) {
        if (target.getNo() == product.getNo()) {
          target.setName(product.getName());
          target.setPrice(product.getPrice());
          target.setStock(product.getStock());
          return "success";
        }
      }
      return "fail";
    }

    private static String deleteProduct(Product product) {
      for (Product target : productList) {
        if (target.getNo() == product.getNo()) {
          productList.remove(target);
          return "success";
        }
      }
      return "fail";
    }
  }


}


