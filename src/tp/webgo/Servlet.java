package tp.webgo;

import tp.server.GameLogic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "server")
public class Servlet extends HttpServlet {
    GameLogic game;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("POST");
        HttpSession session = request.getSession();
        String move = request.getParameter("myMove");
        if (game == null || move.equals("NEW GAME")) {
            game = new GameLogic();
            newGame(request, response);
            prepareNiceButton(request, response);
        }
        else {
            if (move.equals("SURRENDER B")) {
                session.setAttribute("infoLog", "<p id=\"log\">You have surrendered.</p>");
                updateBoard(request,response,false);
                session.removeAttribute("botBoard");
                game = null;
            } else if (move.equals("PAUSE B")) {
                String[] results = game.endGame().split(" ");
                String message;
                if (results[2].equals(results[4])) message = "Tie!<br/>";
                else message = Integer.parseInt(results[2]) > Integer.parseInt(results[2])? "You win!<br/>" : "You lose!<br/>";
                message += "Your score: "+results[2]+"<br/>";
                message += "AI's score: "+results[4]+"<br/>";
                message += "Click on the board to restart.";
                session.setAttribute("infoLog", "<p id=\"log\">"+message+"</p>");
                updateBoard(request,response,false);
                session.removeAttribute("botBoard");
                game = null;
            } else if (move.startsWith("B ")) {
                session.setAttribute("infoLog", "<p id=\"log\">Bad move!</p>");
                if (!game.move(move).equals("MOVE")) {
                    updateBoard(request,response, false);
                    session.setAttribute("infoLog", "<p id=\"log\">"+move+"</p>");
                    updateBoard(request,response, true);
                }
            }
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    void newGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        game.newGame();
        session.removeAttribute("playerBoard");
        session.removeAttribute("botBoard");

        StringBuffer board = new StringBuffer();
        String id = "";
        board.append("<section>");
        board.append("<table>");
        for (int i = 0; i < 19; i++) {
            board.append("<tr>");
            for (int j = 0; j < 19; j++) {
                id = " " + i + " " + j;
                board.append("<td id=\"").append(id).append("\" onclick=\"move(this.id)\"></td>");
            }
            board.append("</tr>");
        }
        board.append("</table>");
        board.append("</section>");
        session.setAttribute("gameBoard", board.toString());

        StringBuffer controls = new StringBuffer();
        controls.append("<button id=\"PAUSE B\" onclick=\"endgame(this.id)\">pass</button>");
        controls.append("<button id=\"SURRENDER B\" onclick=\"endgame(this.id)\">surrender</button>");
        session.setAttribute("controls", controls.toString());
        session.setAttribute("infoLog", "<p id=\"log\">Click on the board to start.</p>");
    }

    void updateBoard(HttpServletRequest request, HttpServletResponse response, boolean bot) throws ServletException, IOException {
        HttpSession session = request.getSession();
        StringBuffer board = new StringBuffer();

        board.append("<script type=\"text/javascript\">");
        if (bot) {
            board.append("setTimeout( function(){");
            board.append("$(\"#log\").text(\"").append(game.getBotMove()).append("\");");
        }
        for (String rem: game.getRemoved()) {
            rem = rem.substring(6);
            board.append("$(\"[id='").append(rem).append("']\").css('backgroundColor','transparent');");
        }
        for (String id: game.getStones().keySet()) {
            board.append("$(\"[id='").append(id).append("']\").css('backgroundColor','").append(game.getStones().get(id)).append("');");
        }
        if (bot) board.append("},400);");
        board.append("</script>");
        if (!bot) session.setAttribute("playerBoard", board.toString());
        else session.setAttribute("botBoard", board.toString());

    }

    void prepareNiceButton(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        StringBuffer button = new StringBuffer();
        button.append("<script type=\"text/javascript\">");
        button.append("$('aside').css('width', '30%');");
        button.append("$(\"[id='NEW GAME']\").css('width', '98.7%');");
        button.append("$(\"[id='NEW GAME']\").css('margin', '0 auto 0.3em 0.1em');");
        button.append("</script>");
        session.setAttribute("niceButton", button.toString());
    }
}
