package kjin.TCP;

import kjin.service.Controller;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Controller controller = new Controller();

        while (true){
            controller.getProductList();
            while (!sc.hasNextInt()) {
                System.out.println("없는 메뉴 입니다. 다시 입력해 주세요");
                sc.next();
            }
            int num = sc.nextInt();
            controller.getMenu(num);
        }
    }
}