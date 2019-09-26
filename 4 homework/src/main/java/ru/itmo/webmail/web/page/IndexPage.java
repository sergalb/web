package ru.itmo.webmail.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class IndexPage extends Page{
    private void action() {
        // No operations.
    }

    private void registrationDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have been registered");
    }

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
    }
    protected void after(HttpServletRequest request, Map<String, Object> view){
        super.after(request, view);
    }

}
