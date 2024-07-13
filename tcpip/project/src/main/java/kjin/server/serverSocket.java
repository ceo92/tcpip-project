package kjin.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class serverSocket {

  public static void main(String[] args) {
    ServerSocket serverSorket = null;	// ServerSocket 초기화

    InputStream is = null;				// InputStream 선언(데이터 받기위해서)
    OutputStream os = null;				// InputStream 선언(데이터 보내기위해서)
    Scanner sc = new Scanner(System.in);

    try {
      serverSorket = new ServerSocket(8000);	// 포트번호 생성
      System.out.println("클라이언트 접속 대기중...");
      Socket socket = serverSorket.accept();	// 클라이언트 접속 대기 후 연결되면 바로 연결
      System.out.println("클라이언트 접속 완료");
      // 2. 데이터 받기
      is = socket.getInputStream();
      os = socket.getOutputStream();

      while (true){
        byte [] inputData = new byte[1024];		// 바이트로 받아야함
        int length  = is.read(inputData);	// 바이트로 읽은 메세지 변수에 저장
        String inputMessage  = new String(inputData, 0, length );	// 저장한 변수 메시지 읽기
        System.out.printf("클라이언트(상대) : %s\n", inputMessage);
        //====================== 받기 완료 ======================

        // 3. 데이터 보내기
        System.out.print("서버(나) : ");
        String outputMessage = sc.nextLine();
        os.write(outputMessage.getBytes());
        os.flush();
        System.out.println("데이터 전송 성공!");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
