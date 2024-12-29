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

@WebServlet("/member/Member.do")
public class MemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static MemberDao memberDao = new MemberDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = (String) req.getParameter("action");
        System.out.println("!! request action : " + action);
        String view = "signup.jsp";
        if (action == null) {
            // 회원 정보 조회
            req.setAttribute("action", "insert");

        } else if ("insert".equals(action)) {
            // 회원가입
            view = "signup.jsp";
            req.setAttribute("action", "insert");
        } else if ("success".equals(action)) {
            // 회원가입 성공
            System.out.println("!! success");
            view = "signup_success.jsp";
        } else if ("get".equals(action) || "update".equals(action) || "delete".equals(action)) {
            // 회원정보 조회
            String id = (String) req.getSession().getAttribute("id");

            // 로그인이 안되어 있는 경우
            if (id == null) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/login/login.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            MemberDto member = memberDao.readMember(id);
            req.setAttribute("member", member);
            view = "mypage.jsp";
            if ("update".equals(action)) {
                view = "update.jsp";
                req.setAttribute("action", "update");
            } else if ("delete".equals(action)) {
                view = "passwordform.jsp";
                req.setAttribute("action", "delete");
            }
        }

        // 페이지로 이동
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/member/" + view);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = (String) req.getParameter("action");
        System.out.println("!! action : " + action);
        if ("insert".equals(action) || action == null) {
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
            resp.sendRedirect("/member/Member.do?action=success");
        } else if ("update".equals(action)) {
            // 회원정보수정
            String id = (String) req.getSession().getAttribute("id");

            // 로그인이 안되어 있는 경우
            if (id == null) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/login/login.jsp");
                dispatcher.forward(req, resp);
            }

            MemberDto member = new MemberDto(
                    id,
                    req.getParameter("name"),
                    req.getParameter("password"),
                    req.getParameter("email"),
                    req.getParameter("address")
            );
            memberDao.updateMember(member);

            // 회원 조회 페이지로 이동
            resp.sendRedirect("/member/Member.do?action=get");
        } else if ("delete".equals(action)) {
            // 회원정보 삭제
            String id = (String) req.getSession().getAttribute("id");

            // 로그인이 안되어 있는 경우
            if (id == null) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/login/login.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            String password = req.getParameter("password");
            memberDao.deleteMember(id, password);

            // 회원가입 페이지로 이동
            resp.sendRedirect("/member/Member.do?action=insert");
        }
    }
}