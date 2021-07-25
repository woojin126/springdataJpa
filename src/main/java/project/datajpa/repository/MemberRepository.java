package project.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.datajpa.dto.MemberDto;
import project.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
   /* List<Member> findByUsername(String username);*/

    /**
        메서드이름으로 쿼리생성.. 메서드이름 관례를보자
        Username And Age : and 조건으로 묶이고,
        그냥 Username 단건이면 = 이퀄
        AgeGreaterThan : age가 파라미터보다 크면
       (이름 다르게하면 안되요)
     */
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    //상위 3개만 조회
    List<Member> findTop3HelloBy();


    /**
     * 실무에서 에도 잘 사용안함...
     *@NamedQuery(
     *         name="Member.findByUsername",
     *         query="select m from Member m where m.username = :username"
     * )
     *
     *   @param 은명화하게 Member엔티티에 명확한 JQPL 이있을떄 :username <- 이게잇을때
     *   --------------------------------------
     *  @Query(name = "Member.findByUsername") // <- 주석처리해도 알아서잘 찾아서 돌아감..
     *  extends JpaRepository<Member,Long>
     *  제네릭타입인 Member + 메서드 명으로 먼저찾음 그다음에 @Query 어노테이션을 훑음
     *  Member.findByUsername
     *
     */
    @Query(name = "Member.findByUsername") // <- 주석처리해도 알아서잘 찾아서 돌아감..
    List<Member> findByUsername(@Param("username") String username);



    /**
     * 최종 가장좋은방법법 @Query, 리포지토리 메소드에 쿼리 정의하기
     * 1.만약 아래쿼리에 오타를치면 애플리케이션 로딩시점에 오류가 발생해줌
     * 2.애플리케이션 로딩시점에 아래 @Query를 다 파싱해놈, 일단 정적쿼리니까 sql을 다만들어놈
     * 근데 문법오류가있네? 그래서 실행시점에 바로 바로 오류를 발생
     */
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username,@Param("age") int age);

    //단순한 값 조회 @Query 이용
    @Query("select m.username from Member m")
    List<String> findUsernameList();
    
    // dto 값 직접조회 ,생성자와 ( ) 괄호안 필드 매칭
    @Query("select new project.datajpa.dto.MemberDto" +
            " (m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    //컬레션 파라미터 바인딩
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    /**
     * 여러 반환타입 이 유연하다.
     */

    List<Member> findListByUsername(String username); //컬렉션 반환
    Member findMemberByUsername(String username); // 단건반환
    Optional<Member> findOptionalByUsername(String username); // 단건 Optional 반환


    /**
     * 스프링 데이터 jpa 페이징
     * 대부분 페이징쿼리의 문제는 totalcount쿼리임, == 성능저하
     * (totalcount 조인을할필요가없는데 조인을해서 가져와서 성능이 망가짐
     * 해결방안: 카운트쿼리를 따로날리는게 좋음(엔 래프트 아우터 조인을 할필요가 없음)
     */
    @Query(value = "select m from Member m left join m.team t "
            , countQuery = "select count(m.username) from Member m"  )
    Page<Member> findByAge(int age, Pageable pageable);

/*    //페이징 슬라이스
    @Query("select m from Member m where m.age = :age")
    Slice<Member> findByAges(@Param("age") int age, Pageable pageable);*/
}
