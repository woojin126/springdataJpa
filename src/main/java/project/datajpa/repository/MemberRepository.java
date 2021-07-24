package project.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long>
{

}
