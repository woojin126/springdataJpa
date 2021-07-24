package project.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.datajpa.entity.Team;

/**
 * JpaRepositort 가보이면 알아서 구현체를 만들어서 프록시로 꽂아버림
 */
public interface TeamRepository extends JpaRepository<Team,Long> {

    
}
