package membercrud;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Repository {

    // 회원 데이터를 저장하는 Map (key = id, value = User 객체)
    Map<String, User> map = new HashMap<>();

    // 회원 저장 (User 객체 생성 후 Map에 저장)
    public boolean saveUser(User user) {
        map.put(user.getId(), user);
        return true;
    }

    // 전체 회원 목록 반환
    public Collection<User> getUser() {
        return map.values();
    }

    // 아이디 존재 여부 확인
    public User findById(String id) {
        return map.get(id);
    }

    // 회원 탈퇴
    public void deleteUser(User user) {
        map.remove(user.getId());
    }
}