package project.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.datajpa.entity.Member;

import java.util.List;

/**
 * 인터페이스끼리 상속했는데 어떻게 기능이 동작을할까? 테스트코드에서 찍어보자
  , 테스트 코드에가보면 나는 @Autowired로 주입을 한것의 정체는? 프록시 객체.
 */
public interface MemberRepository extends JpaRepository<Member,Long>
{
    //만약 인터페이스에 정의되있는 기능말고 내가 만든 기능을 사용하고싶다
    //이렇게만 정의해놨는데 쿼리가 나감;; 이유는?
    //인터페이스를 사용하려면 구현체 클래스를만들고 implements 해서 구현해야하지만
    //인터페이스를 implements 해서 사용하려면 모든 기능을 다가져와서 다시 다 정의해야함 어떻게하지?
    //뒤에서 알아보자
    List<Member> findByUsername(String username);

}
