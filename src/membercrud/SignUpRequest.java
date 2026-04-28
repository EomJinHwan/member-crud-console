package membercrud;

public class SignUpRequest {

    // 회원 정보 필드
    private String id;
    private String pw;
    private String email;
    private String phone;

    // 생성자
    public SignUpRequest(String id, String pw, String email, String phone) {
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.phone = phone;
    }

    // 아이디 반환
    public String getId() {
        return id;
    }

    // 비밀번호 반환
    public String getPw() {
        return pw;
    }

    // 이메일 반환
    public String getEmail() {
        return email;
    }

    // 폰번호 반환
    public String getPhone() {
        return phone;
    }

}