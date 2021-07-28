package project.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.datajpa.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member2",20,teamA);
        Member member3 = new Member("member3",30,teamB);
        Member member4 = new Member("member4",40,teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();//이시점에 디비에 커리를날림
        em.clear();// 영속성 컨텍스트 캐쉬를 싹다 날림

        //확인
        List<Member> members = em.createQuery("select m from Member m ", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("=====>member = " + member.getTeam());
        }
    }

    @Test
    public void JpaEventBaseEntity() throws InterruptedException {
        Member member = new Member("member1");
        memberRepository.save(member); //@PrePersist 발생

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush(); // @PreUpdate
        em.clear();

        //when
        Member findMember = memberRepository.findById(member.getId()).get();

        System.out.println("findMember.getCreateDate() = " + findMember.getCreateDate());
        System.out.println("findMember.getUpdateDate() = " + findMember.getLastModifiedDate());
        System.out.println("findMember.getCreateBy() = " + findMember.getCreateBy());
        System.out.println("findMember.getLastModifiedBy() = " + findMember.getLastModifiedBy());
    }

    @Test
    public void 여러사용자생성마다시간생성() throws InterruptedException {
        List<Member> members = makeList();

        for (Member member : members) {
            test1(member.getId());
            Thread.sleep(1000);
        }
    }
    public List<Member> makeList() {
        List<Member> member = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Member memberd = new Member("woojin" + i);
            member.add(memberd);
        }
        memberRepository.saveAll(member);
        return member;
    }

    protected void test1(Long id){
        Member member = memberRepository.findById(id).get();
        member.setUsername("김우진");
        em.flush();
        //memberRepository.save(member);
    }




}