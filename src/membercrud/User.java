package membercrud;

public class User {

    // 회원 정보 필드
    private String id;
    private String pw;
    private String email;
    private String phone;

    // 생성자
    public User(String id, String pw, String email, String phone) {
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.phone = phone;
    }

    // 아이디 반환
    public String getId() {
        return id;
    }

    // 아이디 수정
    public void setId(String id) {
        this.id = id;
    }

    // 비밀번호 반환
    public String getPw() {
        return pw;
    }

    // 비밀번호 수정
    public void setPw(String pw) {
        this.pw = pw;
    }

    // 이메일 반환
    public String getEmail() {
        return email;
    }

    // 이메일 수정
    public void setEmail(String email) {
        this.email = email;
    }

    // 폰번호 반환
    public String getPhone() {
        return phone;
    }

    // 폰번호 수정
    public void setPhone(String phone) {
        this.phone = phone;
    }
}