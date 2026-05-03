package enzosdev.bjjtrack.config;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    public Long getUserIdToken(Jwt jwt){
        return jwt.getClaim("user_id");
    }

    public Long getAcademyIdToken(Jwt jwt){
        return jwt.getClaim("academy_id");
    }

}
