package pl.edu.pg.eti.kask.wind.user.servlet;

import pl.edu.pg.eti.kask.wind.servletUtils.HttpHeaders;
import pl.edu.pg.eti.kask.wind.servletUtils.ServletUtility;
import pl.edu.pg.eti.kask.wind.user.entity.User;
import pl.edu.pg.eti.kask.wind.user.service.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {AvatarServlet.Paths.AVATAR + "/*",
        AvatarServlet.Paths.AVATAR_UPDATE + "/*",
        AvatarServlet.Paths.AVATAR_SET + "/*",
        AvatarServlet.Paths.AVATAR_DELETE + "/*"
        })
@MultipartConfig(maxFileSize = 200 * 1024)
public class AvatarServlet extends HttpServlet {

    private UserService service;
    public static class Paths {

        public static final String AVATAR = "/api/avatar";
        public static final String AVATAR_UPDATE = "/api/avatar/update";
        public static final String AVATAR_SET = "/api/avatar/set";
        public static final String AVATAR_DELETE = "/api/avatar/delete";
    }

    public static class Patterns {

        /**
         * Specified portrait (for download).
         */
        public static final String AVATAR = "^/[0-9]+/?$";

    }

    @Inject
    public AvatarServlet(UserService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                getAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATAR_UPDATE.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                updateAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATAR_SET.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                setAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATAR_DELETE.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                deleteAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Integer id = Integer.parseInt(userId);
        Optional<User> user = service.find(id);

        if (user.isPresent()) {
            response.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
            if (user.get().getAvatar() != null) {
                response.setContentLength(user.get().getAvatar().length);
                response.getOutputStream().write(user.get().getAvatar());
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void updateAvatar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Integer id = Integer.parseInt(userId);
        Optional<User> user = service.find(id);

        if (user.isPresent()) {
            Part avatar = request.getPart("avatar");
            if (avatar != null) {
                service.updateAvatar(id, avatar.getInputStream());
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void setAvatar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Integer id = Integer.parseInt(userId);
        Optional<User> user = service.find(id);

        if (user.isPresent()) {
            Part avatar = request.getPart("avatar");
            if (avatar != null) {
                service.setAvatar(id, avatar.getInputStream());
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Integer id = Integer.parseInt(userId);
        Optional<User> user = service.find(id);

        if (user.isPresent()) {
            service.deleteAvatar(id);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
