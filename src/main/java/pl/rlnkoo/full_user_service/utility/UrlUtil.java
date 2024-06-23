package pl.rlnkoo.full_user_service.utility;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtil {

    public static String getApplicationUrl(HttpServletRequest request) {
        String appUrl = request.getRequestURL().toString();

        return appUrl.replace(request.getServletPath(), "");
    }
}
