package com.example.jsp.servlet;

import com.example.jsp.model.MemberDao;
import com.example.jsp.model.MemberDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Login.do")
public class LoingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static MemberDao memberDao = new MemberDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = (String) req.getAttribute("action");
        String view = "signup.jsp";
        if(action == null) {
            // 회원 정보 조회
            req.setAttribute("action", "insert");

        } else if("insert".equals(action)) {
            // 회원가입
            view = "signup.jsp";
            req.setAttribute("action", "insert");
        }

        // 회원가입 페이지로 이동
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/member/" + view);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = (String) req.getAttribute("action");
        if("insert".equals(action) || action == null) {
            // 회원가입할 객체 생성
            MemberDto member = new MemberDto(
                    req.getParameter("id"),
                    req.getParameter("name"),
                    req.getParameter("password"),
                    req.getParameter("email"),
                    req.getParameter("address")
            );

            // DB 저장
            memberDao.createMember(member);

            // 회원가입 성공 페이지로 이동
            req.setAttribute("member", member);
            resp.sendRedirect("/member/Member.do?action=select");
        }
    }
}
