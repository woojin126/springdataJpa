package project.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.datajpa.dto.MemberDto;
import project.datajpa.entity.Member;
import project.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember() {
        System.out.println("memberRepository = " + memberRepository.getClass());
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member); //== 비교
    }


    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);
        //단건 조회검색
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);


        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(0);
    }

    //쉬프트 + F6 일괄변경 이름
    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void findHelloBy() {
        List<Member> hello = memberRepository.findTop3HelloBy();
    }

    @Test
    public void testNamedQuery() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);


        List<Member> result = memberRepository.findByUsername("AAA");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);
    }

    @Test
    public void testQuery최종형식() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);


        List<Member> result = memberRepository.findUser("AAA", 10);
        assertThat(result.get(0)).isEqualTo(m1);

    }

    //단순한 값 조회 @Query 이용
    @Test
    public void findUsernameList() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    //DTO 직접조회 @qUERY이용
    @Test
    public void dto직접조회() {

        Team team = new Team("teamA");
        teamRepository.save(team);
        Member m1 = new Member("AAA", 10);
        memberRepository.save(m1);
        m1.setTeam(team);


        List<MemberDto> memberDto = memberRepository.findMemberDto();

        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }

    //임베디드 타입 , 컬렉션파라미터 바인딩
    @Test
    public void 컬렉션파라미터바인딩() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);


        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));

        for (Member member : result) {
            System.out.println("member = " + member);
        }

    }

        @Test
        public void 반환타입이유연함() {
            Member m1 = new Member("AAA", 10);
            Member m2 = new Member("BBB", 20);
            memberRepository.save(m1);
            memberRepository.save(m2);

            //이상한값을넣어서 반환값이 없어도 null이아닌 empty()를 반환해줌, 반환값 LIST도 empty로 반환해줌
            //List<Member> aaa = memberRepository.findListByUsername("sfasefasef");
            Optional<Member> aaa = memberRepository.findOptionalByUsername("AAA");
            System.out.println("aaa = " + aaa.get());
            //결국 옵셔널도 단건조회라 같은값 AAA가 두개있으면 에외가터짐




            //단건조회는 없으면 null 임 ㅠㅠ,, list , optional은 반환값 empty()로줌
            //그냥 데이터가 있을지 없을지모르면 그냥 무조건 Optional 써그냥
            Member findMember = memberRepository.findMemberByUsername("saefeasfs");
            System.out.println("findMember = " + findMember);

        }

    @Test//대부분 페이징쿼리의 문제는 totalcount쿼리임, == 성능저하 (join이있다면
    public void 스프링데이터jpa페이징(){
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

        int age = 10;
        //0페이지에서 3개가져오고 정렬해, PageRequest 부모가 Pageable
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //반환값이 Page면 자기가알아서 totalcount 까지 나옴 ㄷㄷ, 절떄로이거 컨트롤러에서 그대로반환하면 (DTO로변환해) 안된다
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        //dto로 반환. 에는이제 API로 반환해도된다!
        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
     /*   //간단히 몇개만가져올거면
        memberRepository.findTop3ByAge(age);*/


        //페이지에 데이터 3개를 꺼내고싶으면
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        for (Member member : content) {
            System.out.println("member = " + member);
        }
        System.out.println("totalElements = " + totalElements);

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0); //페이지 번호계산까지
        assertThat(page.getTotalPages()).isEqualTo(2); // 전체 페이지 개수
        assertThat(page.isFirst()).isTrue(); //첫페이지인가?
        assertThat(page.hasNext()).isTrue();//다음페이지가 있는가?



    }

  /*  @Test//한번에 다바꾸기 쉬프트 에프육,슬라이스는 페이지 개념이없음 토탈페이지가없음, 쭊쭊 더보기로 내리기같은것
    @Rollback(value = false)
    public void 스프링데이터jpa페이징슬라이스방법(){
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

        int age = 10;
        //0페이지에서 3개가져오고 정렬해, PageRequest 부모가 Pageable
        //슬라이스면 3 개면 +1인 4개를 요청함
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //반환값이 Page면 자기가알아서 totalcount 까지 나옴 ㄷㄷ
        Slice<Member> page = memberRepository.findByAges(age, pageRequest);
       *//* 단순히 그냥 데이터 몇개만 가져오고싶으면 list로 받아도된다
        List<Member> pages = memberRepository.findByAges()*//*
        //페이지에 데이터 3개를 꺼내고싶으면
        List<Member> content = page.getContent();

        for (Member member : content) {
            System.out.println("member = " + member);
        }

        assertThat(content.size()).isEqualTo(3);
        //assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0); //페이지 번호계산까지
        //assertThat(page.getTotalPages()).isEqualTo(2); // 전체 페이지 개수
        assertThat(page.isFirst()).isTrue(); //첫페이지인가?
        assertThat(page.hasNext()).isTrue();//다음페이지가 있는가?



    }*/

    @Test
    @Rollback(value = false)
    public void 벌크업데이트(){
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",13));
        memberRepository.save(new Member("member3",21));
        memberRepository.save(new Member("member4",31));
        memberRepository.save(new Member("member5",41));

        int resultCount = memberRepository.bulkAgePlus(20);
        /*
        플러시 호출 타이밍.
        1. em.flush() 을 통한 직접 호출
        2. 트랜잭션 커밋 시 플러시 자동 호출
        3. JPQL 쿼리 실행 시 플러시 자동 호출*/
         //em.clear();//영속컨텍스트 깔끔히날림
        //플러쉬 클리어해주는이유. 벌크연산은 영속성컨텍스트와 상관없이 DB에 접근
        //DB에는분명히 업데이트가되었는데 영속성컨텍스트에 값은 그대로 남아있음;

        List<Member> result = memberRepository.findByUsername("member5");
        Member member5 = result.get(0);
        System.out.println("member5 = " + member5);

        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        //when
        List<Member> members = memberRepository.findMemberEntityGraph();
        /**
         * member만 db에서 끌어온다.
         * Team은 어떻게하나? lazy로딩이라 일단 Team은 프록시라는 가짜객체를 만들어둠( 텅텅빈)
         * 실제 팀필드메서드를 가져올떄 진짜 데이터를 가져옴옴         */

        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
            System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
            System.out.println("===========>member.getTeam().getName() = " + member.getTeam().getName());
        }
    }

    @Test
    public void queryHint(){
        //given
        Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();//여기까지실행해도 영속성 컨텍스트에 남아있다!
        em.clear();//이시점에 다 날라감

        //when
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");
//변경감지의 단점 , 원본이있어야해 객체를 2개관리하는거나 마찬가지 (변경하기위해 원래데이터가 있어야겠지?)

        em.flush();
        //then

    }

    @Test
    public void Lock(){
        //given
        Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();//여기까지실행해도 영속성 컨텍스트에 남아있다!
        em.clear();//이시점에 다 날라감

        //when
        List<Member> result = memberRepository.findLockByUsername("member1");


    }
}