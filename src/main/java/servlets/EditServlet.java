package servlets;

import model.User;
import service.UserService;
import service.UserServiceJdbc;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {

    UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long id = Long.parseLong(req.getParameter("id"));
        User user = userService.findUserById(id);
        req.setAttribute("userId", id);
        req.setAttribute("userLogin", user.getLogin());
        req.setAttribute("userAge", user.getAge());
        req.setAttribute("userEmail", user.getEmail());
        req.setAttribute("userPassword", user.getPassword());
        req.setAttribute("user", user);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/edit.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long id = Long.parseLong(req.getParameter("id"));
        String login = req.getParameter("login");
        int age = Integer.parseInt(req.getParameter("age"));
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User(id, login, age, email, password);
        if (userService.findUserByLogin(user.getLogin()) == null || (userService.findUserByLogin(login).getId() == user.getId()))  {
            user.setAge(age);
            user.setPassword(password);
            user.setEmail(email);
            user.setLogin(login);
            userService.updateUser(user);
            String path = req.getContextPath() + "/list";
            resp.sendRedirect(path);
        }  else {
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter wr = resp.getWriter();
            wr.println("Пользователь с таким логином существует");
            wr.close();
        }

    }
}
