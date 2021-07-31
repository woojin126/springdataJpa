package project.datajpa.repository;

//중첩구조 Projections
public interface NestedClosedProjections {

    String getUsername();
    TeamInfo getTeam(); //쿼리를보다싶이 getUsername은 최적화가되지만, 그다음부터 TEAM은 최적화가 안됨

    interface TeamInfo{ 
        String getName();
    }
    /*
        select
        member0_.username as col_0_0_,
        team1_.team_id as col_1_0_,
        team1_.team_id as team_id1_2_,
        team1_.name as name2_2_ 
    from
        member member0_ 
    left outer join
        team team1_ 
            on member0_.team_id=team1_.team_id 
    where
        member0_.username=?
     */
}
