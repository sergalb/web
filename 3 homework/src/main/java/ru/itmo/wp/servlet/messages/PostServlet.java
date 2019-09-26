package ru.itmo.wp.servlet.messages;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/message/auth", "/message/findAll", "/message/add"})
public class PostServlet extends HttpServlet {

    class Pair {
        private String user, text;
        Pair(String user, String text){
            this.user = user;
            this.text = text;
        }
    }

    private List<Pair> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getServletPath();
        HttpSession session = request.getSession(true);
        String output = "";
        switch (action) {
            case "/message/auth":
                String user;
                Object userInSession = session.getAttribute("user");
                if (userInSession == null) {
                    user = request.getParameter("user");
                    if (user != null) {
                        session.setAttribute("user", user);
                    } else {
                        user = "";
                    }
                } else {
                    user = userInSession.toString();
                }
                output = new Gson().toJson(user);
                break;
            case "/message/findAll":
                output = new Gson().toJson(messages);
                break;
            case "/message/add":
                String text = request.getParameter("text");
                if (text == null) {
                    text = "";
                }
                messages.add(new Pair(session.getAttribute("user").toString(), text));
                output = new Gson().toJson(messages);
                break;
        }
        response.setContentType("application/json");
        response.getWriter().print(output);
        response.getWriter().flush();
    }
}
