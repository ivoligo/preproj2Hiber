package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import model.User;
import service.UserService;
import service.UserServiceJdbc;

@WebServlet("/add")
public class AddServlet extends HttpServlet {

    UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/add.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        int age = Integer.parseInt(req.getParameter("age"));
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User(login, age, email, password);

        if( ( userService.findUserByLogin(login) == null) ){
            userService.AddUser(user);
            String path = req.getContextPath() + "/list";
            resp.sendRedirect(path);
        }
        else {
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter wr = resp.getWriter();
            wr.println("Пользователь существует");
            wr.close();
        }

    }
}
