package ru.itmo.webmail.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class NotFoundPage extends Page{
    public void action() {
        // No operations.
    }
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
    }
    protected void after(HttpServletRequest request, Map<String, Object> view){
        super.after(request, view);
    }
}
