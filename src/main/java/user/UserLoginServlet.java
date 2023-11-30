package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		
		if (null == userID || userID.equals("") || null == userPassword || userPassword.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력해주세요");
			response.sendRedirect("login.jsp");
			return;
		}
		int result = new UserDAO().login(userID, userPassword);
		if (1 == result) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공메세지");
			request.getSession().setAttribute("messageContent", "로그인 성공");
			response.sendRedirect("index.jsp");
		}
		else if (2 == result) {
			request.getSession().setAttribute("messageType", "오류메세지");
			request.getSession().setAttribute("messageContent", "비밀번호를 다시 확인하세요");
			response.sendRedirect("login.jsp");
		} else if (0 == result) {
			request.getSession().setAttribute("messageType", "오류메세지");
			request.getSession().setAttribute("messageContent", "아이디를 다시 확인하세요");
			response.sendRedirect("login.jsp");
		} else if (-1 == result) {
			request.getSession().setAttribute("messageType", "오류메세지");
			request.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생하였습니다");
			response.sendRedirect("login.jsp");
		}
	}

}
