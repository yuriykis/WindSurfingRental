package pl.edu.pg.eti.kask.wind.user.servlet;

import pl.edu.pg.eti.kask.wind.user.service.UserService;

import javax.inject.Inject;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(urlPatterns = AvatarServlet.Paths.PORTRAITS + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class AvatarServlet extends HttpServlet {

    private UserService service;
    public static class Paths {

        public static final String PORTRAITS = "/api/avatars";

    }

    @Inject
    public AvatarServlet(UserService service) {
        this.service = service;
    }

}
