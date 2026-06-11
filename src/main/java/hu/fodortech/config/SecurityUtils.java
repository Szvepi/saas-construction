package hu.fodortech.config;

import hu.fodortech.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SecurityUtils {

    private final JwtService jwtService;

    public SecurityUtils(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String getCurrentUserCompanyId() {
        // Try to get companyId from JWT token in request
        HttpServletRequest request = getCurrentHttpRequest();
        if (request != null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (jwtService.validateToken(token)) {
                    return jwtService.extractCompanyId(token);
                }
            }
        }

        // Fallback: try to get from SecurityContext (if user is authenticated)
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            // For now, return a default - in a real app you'd store companyId in UserDetails
            return "default-company";
        }

        return null;
    }

    private HttpServletRequest getCurrentHttpRequest() {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return attrs.getRequest();
        } catch (Exception e) {
            return null;
        }
    }
}

