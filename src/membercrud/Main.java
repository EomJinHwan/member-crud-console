package membercrud;

public class Main {
    public static void main(String[] args) {

        // 저장소 객체 생성 (데이터 관리 담당)
        Repository rs = new Repository();

        // 비즈니스 로직 객체 생성 (Repository 의존)
        UserService us = new UserService(rs);

        // UI 객체 생성 (Service 의존)
        Ui ui = new Ui(us);

        // 프로그램 시작
        ui.menu();
    }
}