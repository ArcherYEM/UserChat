package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChatSubmitServlet")
public class ChatListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String fromID = request.getParameter("fromID");
        String toID = request.getParameter("toID");
        String listType = request.getParameter("listType");
        String chatContent = request.getParameter("chatContent");
        if (null == fromID || fromID.equals("") || null == toID || toID.equals("") || null == chatContent
                || chatContent.equals("")) {
            response.getWriter().write("0");
        } else if (listType.equals("text")) {
            response.getWriter().write(getTen(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8")));
        } else {
            try {
                response.getWriter().write(getID(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8"), listType));
            } catch (Exception e) {
                response.getWriter().write("");
            }
        }
    }

    public String getTen(String fromID, String toID) {
        StringBuffer result = new StringBuffer("");
        result.append("{\"result\":[");
        ChatDAO chatDAO = new ChatDAO();
        ArrayList<ChatDTO> chatList = chatDAO.getChatListByRecent(fromID, toID, 10); // "getChatListByResent"에서 "getChatListByRecent"로 수정
        if (0 == chatList.size())
            return "";
        for (int i = 0; i < chatList.size(); i++) {
            result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
            if (i != chatList.size() - 1)
                result.append(",");
        }
        result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
        return result.toString();
    }

    public String getID(String fromID, String toID, String chatID) {
        StringBuffer result = new StringBuffer("");
        result.append("{\"result\":[");
        ChatDAO chatDAO = new ChatDAO();
        ArrayList<ChatDTO> chatList = chatDAO.getChatListByID(fromID, toID, chatID);
        if (0 == chatList.size())
            return "";
        for (int i = 0; i < chatList.size(); i++) {
            result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
            result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
            if (i != chatList.size() - 1)
                result.append(",");
        }
        result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
        return result.toString();
    }
}