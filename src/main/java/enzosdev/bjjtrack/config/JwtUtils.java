package enzosdev.bjjtrack.config;

import enzosdev.bjjtrack.enums.ScopeName;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtUtils {

    public Long getUserIdToken(Jwt jwt){
        return jwt.getClaim("user_id");
    }

    public Long getAcademyIdToken(Jwt jwt){
        return jwt.getClaim("academy_id");
    }

    public boolean isPlatformAdmin(Jwt jwt){
        List<String> scopes = jwt.getClaim("scope");
        return scopes != null && scopes.contains(ScopeName.PLATFORM_ADMIN.getValue());
    }

}
