package project.datajpa.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {


    @PersistenceContext
    EntityManager em;
    List<Member> findAllMembers(){
       return  em.createQuery("select m from Member m ",Member.class)
                .getResultList();
    }
}
