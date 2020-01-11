<%@ page import="java.util.LinkedHashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Ubuntu+Mono&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
    <title>webGO</title>
</head>

<body>
<div>
    ${sessionScope.gameBoard}
    <aside>
        <button id="NEW GAME" onclick="newGame()" style="width: 99%;">new game</button>
        ${sessionScope.controls}
        <button id="PAUSE B" onclick="endgame(this.id)">pause</button>
        <button id="SURRENDER B" onclick="endgame(this.id)">surrender</button>
        <p id="log">Click on the board to start</p>
        <form action="server" method="post" id="moveForm">
            <input type="hidden" name="myMove" id="myMove"/>
        </form>
    </aside>
</div>

<script type="text/javascript">
function newGame() {
    $("aside").css('float', 'left');
    $('[name=myMove]').val("NEW GAME");
    $("#moveForm").submit();
}


function move(clickedId)
    {
      var color = document.getElementById(clickedId).style.backgroundColor;
      if (color == "black" || color == "white") {
        document.getElementById("log").textContent = "Bad move";
      } else {
          document.getElementById("myMove").value = "B" + clickedId;
          document.getElementById("moveForm").submit();
          alert(document.getElementById("badMove").value);
          document.getElementById("log").textContent = "B" + clickedId;
      }
    }

function endgame(message) {
      document.getElementById("log").textContent = message;
}

</script>
</body>

</html>