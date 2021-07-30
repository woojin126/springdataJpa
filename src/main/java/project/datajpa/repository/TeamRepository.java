package project.datajpa.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.datajpa.entity.Team;

import java.util.List;

/**
 * JpaRepositort 가보이면 알아서 구현체를 만들어서 프록시로 꽂아버림
 */
public interface TeamRepository extends JpaRepository<Team,Long> {


/*    @Query("select t from Team t " +
            "join fetch t.members")*/
@EntityGraph(attributePaths = {"members"})
    List<Team> findEntityGraphByName(String name);
    
}
