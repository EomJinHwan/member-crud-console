package membercrud;

import java.util.Collection;

public class UserService {

    // Repository 의존성
    private final Repository rs;

    // 생성자를 통한 의존성 주입
    public UserService(Repository rs) {
        this.rs = rs;
    }

    // 아이디 중복 검사 (Repository 호출)
    public void isDuplicatedId(String id) {
        User user = rs.findById(id);
        if (user != null) {
            throw new IllegalArgumentException("중복된 아이디 입니다. 다시 입력해 주세요");
        }
        if (id.contains(" ")) {
            throw new IllegalArgumentException("아이디에는 공백을 넣을 수 없습니다");
        }
    }

    // 비밀번호 검증
    public void validatePw(String pw) {
        if (pw.length() < 5) {
            throw new IllegalArgumentException("비밀번호를 5자리 이상으로 해주세요");
        }
        if (pw.contains(" ")) {
            throw new IllegalArgumentException("비밀번호에는 공백을 넣을 수 없습니다");
        }
        if (!pw.matches(".*[!@#$%^&*].*")) {
            throw new IllegalArgumentException("특수문자를 최소 하나 이상 포함시켜 주세요");
        }
    }

    // 이메일 형식 검증
    public void validateEmail(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다");
        }
        if (email.contains(" ")) {
            throw new IllegalArgumentException("이메일에는 공백을 넣을 수 없습니다");
        }
    }

    // 전화번호 형식 검증
    public void validatePhone(String phone) {
        if (!phone.matches("\\d+")) {
            throw new IllegalArgumentException("전화번호는 숫자만 입력해야 합니다");
        }
        if (phone.length() != 11) {
            throw new IllegalArgumentException("전화번호는 11자리어야 합니다");
        }
        if (!phone.startsWith("010")) {
            throw new IllegalArgumentException("전화번호는 010으로 시작해야 합니다");
        }
        if (phone.contains(" ")) {
            throw new IllegalArgumentException("전화번호에는 공백을 넣을 수 없습니다");
        }
    }

    // 전화번호 형식 변경
    public String formatPhone(String phone) {
        return phone.substring(0, 3) + "-"
                + phone.substring(3, 7) + "-"
                + phone.substring((7));
    }


    // 전체 회원 목록 조회
    public Collection<User> getAllUser() {
        Collection<User> users = rs.getUser();
        // 회원이 없는 경우
        if (users.isEmpty()) {
            throw new IllegalArgumentException("존재하는 회원이 없습니다");
        }
        return users;
    }


    // 회원가입 처리 (Repository에 저장 요청)
    public boolean signUp(SignUpRequest sur) {
        // DTO -> User 변환
        User user = new User(
                sur.getId(),
                sur.getPw(),
                sur.getEmail(),
                sur.getPhone()
        );
        return rs.saveUser(user);
    }

    // 로그인
    public boolean login(String id, String inputPw) {
        User user = rs.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("일치하는 아이디가 없습니다. 아이디를 확인해주세요");
        }
        if (!user.getPw().equals(inputPw)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        return true;
    }

    // 아이디 존재 여부 확인
    public User findById(String id) {
        User user = rs.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("존재하는 회원이 없습니다 아이디를 확인해주세요");
        }
        return user;
    }

    // 비밀번호 수정
    public void updatePw(User user, String currentPw, String newPw) {
        if (!user.getPw().equals(currentPw)) {
            throw new IllegalArgumentException("비밀번호가 틀립니다 다시 입력해주세요");
        }
        if (currentPw.equals(newPw)) {
            throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호는 사용할 수 없습니다");
        }
        validatePw(newPw);
        user.setPw(newPw);
    }

    // 이메일 수정
    public void updateEmail(User user, String currentEmail, String newEmail) {
        if (!user.getEmail().equals(currentEmail)) {
            throw new IllegalArgumentException("이메일이 틀립니다 다시 입력해주세요");
        }
        if (currentEmail.equals(newEmail)) {
            throw new IllegalArgumentException("기존 이메일과 동일한 이메일을 사용할 수 없습니다");
        }
        validateEmail(newEmail);
        user.setEmail(newEmail);
    }

    // 전화번호 수정
    public void updatePhone(User user, String currentPhone, String newPhone) {
        if (!user.getPhone().equals(formatPhone(currentPhone))) {
            throw new IllegalArgumentException("전화번호가 틀립니다 다시 입력해주세요");
        }
        if (currentPhone.equalsIgnoreCase(newPhone)) {
            throw new IllegalArgumentException("기존 전화번호와 동일한 번호를 사용할 수 없습니다");
        }
        validatePhone(newPhone);
        user.setPhone(formatPhone(newPhone));
    }

    // 회원 탈퇴
    public void deleteUser(User user) {
        rs.deleteUser(user);
    }
}