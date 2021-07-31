package project.datajpa.repository.projection;

//클래스기반 Projection == 생성자의 파라미터 이름으로 매칭시켜서 프로젝션시킴,프록시가 필요없음
public class UsernameOnlyDto {

    private final String username;

    public UsernameOnlyDto(String username){
        this.username=username;
    }

    public String getUsername(){
        return username;
    }
}
