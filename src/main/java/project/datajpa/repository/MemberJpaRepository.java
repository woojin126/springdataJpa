package project.datajpa.repository;

import org.springframework.stereotype.Repository;
import project.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {

    @PersistenceContext
    EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public void delete(Member member){
        em.remove(member);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m ", Member.class)
                .getResultList();
    }

    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public long count(){
        return em.createQuery("select count(m) from Member m ", Long.class)
                .getSingleResult();//단건
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findByUsernameAndAgeGreaterThen(String username, int age){

        return em.createQuery("select m from Member m where m.username =:username and m.age > :age", Member.class)
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    //네임드쿼리 호출 실무에서 사용 x
    public List<Member> findByUsername(String username){

       return em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    //순수 JPA 페이징
    //방언이라는게 존재하기때문에 db가 바뀌어도 쿼리가 알아서 바뀌어서 나감
    public List<Member> findByPage(int age, int offset, int limit){
       return  em.createQuery("select m from Member m where m.age = :age order by m.username desc ",Member.class)
                .setParameter("age", age)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public long totalCount(int age){
        return em.createQuery("select count(m) from Member m where m.age = :age ", Long.class)
                .setParameter("age", age)
                .getSingleResult();

    }

    //벌크성 쿼리
    public int bulkAgePlus(int age){
        return  em.createQuery("update Member m set m.age = m.age + 1 where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate();


    }
}
