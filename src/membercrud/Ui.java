package membercrud;

import java.util.Collection;
import java.util.Scanner;

public class Ui {

    // Service 의존
    private final UserService us;

    // 입력을 위한 Scanner
    private Scanner sc;

    // 생성자
    public Ui(UserService us) {
        this.us = us;
    }

    // 메뉴 화면 및 반복 루프
    public void menu() {
        sc = new Scanner(System.in);

        while (true) {
            System.out.println("------------------------------------------------------");
            System.out.println("1. 회원가입 | 2. 회원확인 | 3. 로그인 | 4. 회원 수정 | 5. 종료 ");
            System.out.println("------------------------------------------------------");
            System.out.print("메뉴를 골라주세요 : ");

            String menu = sc.nextLine();

            switch (menu) {
                case "1":
                    signUpUi();      // 회원가입 실행
                    break;
                case "2":
                    printUserUi();   // 회원 목록 출력
                    break;
                case "3":
                    loginUi();       // 로그인 실행
                    break;
                case "4":
                    userEdit();      // 회원 수정 실행
                    break;
                case "5":
                    System.out.println("시스템을 종료합니다");
                    return;
                default:
                    System.out.println("메뉴를 다시 골라주세요");
            }
        }
    }

    // 회원가입 UI 처리
    public void signUpUi() {
        String id = inputId();
        if (id == null) return;
        String pw = inputPw();
        if (pw == null) return;
        String email = inputEmail();
        if (email == null) return;
        String phone = inputPhone();
        if (phone == null) return;

        // DTO 객체 생성
        SignUpRequest sur = new SignUpRequest(id, pw, email, phone);

        // 회원가입 요청
        boolean result = us.signUp(sur);

        if (result) {
            System.out.println("회원가입이 완료되었습니다");
        } else {
            System.out.println("회원가입에 문제가 생겼습니다 다시 시도해주세요");
        }
    }

    // 회원 아이디 입력
    public String inputId() {
        while (true) {
            try {
                System.out.print("아이디를 입력해주세요(뒤로가기 0) : ");
                String id = sc.nextLine();
                if (id.equals("0")) {
                    return null;
                }

                // 중복이면 true / 아니면 false 반환
                us.isDuplicatedId(id);
                return id;  // 입력받은 id 반환

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    // 회원 비밀번호 입력
    public String inputPw() {
        //비밀번호 입력 및 길이 검증
        while (true) {
            try {
                System.out.print("비밀번호를 입력해주세요(뒤로가기 0) : ");
                String pw = sc.nextLine();
                if (pw.equals("0")) {
                    return null;
                }

                // 비밀번호 검증
                us.validatePw(pw);
                return pw;  // 입력받은 pw 반환

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 회원 email 입력
    public String inputEmail() {
        // 이메일 입력 밑 형식 확인
        while (true) {
            try {
                System.out.print("이메일을 입력해주세요(뒤로가기 0) : ");
                String email = sc.nextLine();
                if (email.equals("0")) {
                    return null;
                }

                // @들어가면 true / 아니면 false
                us.validateEmail(email);
                return email;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 회원 전환번호 입력
    public String inputPhone() {
        // 전화번호 입력 및 형식 변환
        while (true) {
            try {
                System.out.print("전화번호를 입력해 주세요(뒤로가기 0) : ");
                String phone = sc.nextLine();
                if (phone.equals("0")) {
                    return null;
                }

                us.validatePhone(phone);
                return us.formatPhone(phone);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 회원 목록 출력 UI
    public void printUserUi() {
        try {
            // Service로부터 회원 목록 받아오기
            Collection<User> users = us.getAllUser();

            // 회원 목록 출력
            System.out.println("회원 목록");
            for (User user : users) {
                System.out.println("아이디 : " + user.getId() + ", 비밀번호 : " + user.getPw() + ", 이메일 : " + user.getEmail() + ", 전화번호 : " + user.getPhone());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // 로그인 ui
    public void loginUi() {
        while (true) {
            try {
                System.out.print("아이디를 입력해 주세요(뒤로가기 0) : ");
                String id = sc.nextLine();
                if (id.equals("0")) {
                    return;
                }
                System.out.print("비밀번호를 입력해 주세요(뒤로가기 0) : ");
                String inputPw = sc.nextLine();
                if (inputPw.equals("0")) {
                    return;
                }

                if (us.login(id, inputPw)) {
                    System.out.println("로그인이 완료되었습니다");
                    return;
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //회원 수정 Ui
    public void userEdit() {
        System.out.print("아이디를 입력해주세요(뒤로가기 0) : ");
        String inputId = sc.nextLine();
        if (inputId.equals("0")) {
            return;
        }
        while (true) {
            User user = us.findById(inputId);
            try {
                System.out.print("변경 메뉴를 선택해주세요 | 1. 비밀번호 변경 | 2. 이메일 변경 | 3. 휴대전화 변경 | 4. 회원 탈퇴 | 5. 종료 : ");
                String menu = sc.nextLine();
                switch (menu) {
                    case "1":
                        updatePw(user);
                        break;
                    case "2":
                        updateEmail(user);
                        break;
                    case "3":
                        updatePhone(user);
                        break;
                    case "4":
                        deleteUser(user);
                        return;
                    case "5":
                        return;
                    default:
                        System.out.println("메뉴를 확인해주세요");
                        break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 비밀번호 수정
    public void updatePw(User user) {
        while (true) {
            try {
                System.out.print("현재 비밀번호를 입력해주세요 (돌아가기 0) : ");
                String currentPw = sc.nextLine();
                if (currentPw.equals("0")) return;
                System.out.print("변경 할 비밀번호를 입력해주세요 (돌아가기 0) : ");
                String newPw = sc.nextLine();
                if (newPw.equals("0")) return;

                // 비밀번호 수정
                us.updatePw(user, currentPw, newPw);
                System.out.println("비밀번호를 변경했습니다");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    // 이메일 수정{
    public void updateEmail(User user) {
        while (true) {
            try {
                System.out.print("현재 이메일을 입력해주세요 (돌아가기 0) : ");
                String currentEmail = sc.nextLine();
                if (currentEmail.equals("0")) return;
                System.out.print("변경할 이메일을 입력해주세요 (돌아기기 0) : ");
                String newEmail = sc.nextLine();
                if (newEmail.equals("0")) return;

                // 이메일 수정
                us.updateEmail(user, currentEmail, newEmail);
                System.out.println("이메일을 변경했습니다");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 전화번호 수정
    public void updatePhone(User user) {
        while (true) {
            try {
                System.out.print("현재 전화번호를 입력해주세요 (돌아기기 0) : ");
                String currentPhone = sc.nextLine();
                if (currentPhone.equals("0")) return;
                System.out.print("변경할 전화번호를 입력해주세요 (돌아기기 0) : ");
                String newPhone = sc.nextLine();
                if (newPhone.equals("0")) return;

                // 전화번호 수정
                us.updatePhone(user, currentPhone, newPhone);
                System.out.println("전화번호를 변경했습니다");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 회원 탈퇴
    public void deleteUser(User user) {
        while (true) {
            try {
                System.out.print("회원탈퇴를 하시겠습니까 Y / N : ");
                String result = sc.nextLine();
                if (result.equalsIgnoreCase("n")) return;
                if (!result.equalsIgnoreCase("y")) {
                    throw new IllegalArgumentException("Y(y) 또는 N(n)만 입력해주세요");
                }

                System.out.println("회원탈퇴를 진행하겠습니다");
                us.deleteUser(user);
                System.out.println("회원탈퇴가 완료되었습니다");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}