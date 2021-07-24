package project.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.datajpa.entity.Member;

/**
 * 인터페이스끼리 상속했는데 어떻게 기능이 동작을할까? 테스트코드에서 찍어보자
  , 테스트 코드에가보면 나는 @Autowired로 주입을 한것의 정체는? 프록시 객체.
 */
public interface MemberRepository extends JpaRepository<Member,Long>
{

}
