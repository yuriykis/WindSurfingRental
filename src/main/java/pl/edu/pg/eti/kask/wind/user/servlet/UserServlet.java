package pl.edu.pg.eti.kask.wind.user.servlet;

import pl.edu.pg.eti.kask.wind.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.wind.user.entity.User;
import pl.edu.pg.eti.kask.wind.user.service.UserService;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = UserServlet.Paths.USER)
public class UserServlet extends HttpServlet {

    private UserService service;

    public static class Paths {

        public static final String USER = "/api/user";

    }

    @Inject
    public UserServlet(UserService service) {
        this.service = service;
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");
        if (userId != null) {
            Integer id = Integer.parseInt(userId);
            Optional<User> user = service.find(id);
            if (user.isPresent()) {
                response.getWriter().write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
                return;
            }
        }
        response.getWriter().write("{}");
    }
}
