package project.datajpa.repository;

import lombok.RequiredArgsConstructor;
import project.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 규칙이있다 
 * MemberRepository + Impl 규칙을 맞춰줘야 한다. 그래야 DATA JPA 가 알아서 조립을해서 CALL했을때 구현체를 찾아서 호출해준다
 *---> 그런데 이렇게해도 어차피  MemberRepository에 다몰아 넣는것이라
 * 핵심 비지니스로직과, 단순 화면 에 맞춘(dto를이용해 필요한 정보만 뽑는) 쿼리들을 분리하는게 낫다 (따로 클래스로 repository륾 만드는게 낫다 */
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m",Member.class )
               .getResultList();
    }
}
