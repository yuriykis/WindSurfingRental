package pl.edu.pg.eti.kask.wind.user.servlet;

import pl.edu.pg.eti.kask.wind.user.dto.GetAllUsersResponse;
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
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = UsersServlet.Paths.USER)
public class UsersServlet extends HttpServlet {
    private UserService service;

    public static class Paths {

        public static final String USER = "/api/users";

    }

    @Inject
    public UsersServlet(UserService service) {
        this.service = service;
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = service.findAll();
        response.getWriter().write(jsonb.toJson(GetAllUsersResponse.entityToDtoMapper().apply(users)));
        return;
    }
}
