package com.example.jsp.servlet;

import com.example.jsp.model.MemberDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/Login.do")
public class LoingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static MemberDao memberDao = new MemberDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = (String) req.getParameter("action");
        System.out.println("!! action : " + action);
        String view = "login.jsp";

        if(action == null) {
            // 로그아웃 성공
            req.getSession().invalidate(); // 세션 무효화
        } else if("success".equals(action)) {
            // 로그인 성공
            view = "login_success.jsp";
            req.setAttribute("message", req.getSession().getAttribute("id")+"님 환영합니다.");
        } else if("fail".equals(action)) {
            // 로그인 실패
            view = "login_fail.jsp";
            req.getSession().invalidate(); // 세션 무효화
            req.setAttribute("message", "ID/PW가 없습니다.");
        } else if("login".equals(action)) {
            // 로그인 페이지 이동
        }

        // 로그아웃 성공 페이지로 이동
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/login/" + view);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = (String) req.getParameter("action");
        String url = "Login.do";

        if(action == null) {
            String result = memberDao.readMemberPassword(req.getParameter("id"), req.getParameter("password"));

            // 로그인 성공
            if("success".equals(result)) {
                url += "?action=success";
                HttpSession session = req.getSession(); // 세션 생성
                session.setAttribute("id", req.getParameter("id"));
            }
            // 로그인 실패
            else if("fail".equals(result)) {
                url += "?action=fail";
            }
        }

        // 로그인 성공 페이지로 이동
        resp.sendRedirect(url);
    }
}
