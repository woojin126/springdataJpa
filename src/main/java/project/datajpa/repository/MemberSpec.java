package project.datajpa.repository;

import org.springframework.data.jpa.domain.Specification;
import org.thymeleaf.util.StringUtils;
import project.datajpa.entity.Member;

import javax.persistence.criteria.*;

public class MemberSpec {

    public static Specification<Member> teamName(final String teamName){
       return (root, query, criteriaBuilder) -> {

           if(StringUtils.isEmpty(teamName)){
               return null;
           }

           Join<Object, Object> t = root.join("team", JoinType.INNER);//회원과조인
           return criteriaBuilder.equal(t.get("name"), teamName);
       };
    }

    public static Specification<Member> username(final String username){
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("username"), username);
    }
}
