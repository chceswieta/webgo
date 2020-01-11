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
        <button id="NEW GAME" onclick="newGame()" style="display: block; width: 50%; margin: auto">new game</button>
        ${sessionScope.controls}
        ${sessionScope.infoLog}
    </aside>



    <form action="server" method="post" id="moveForm">
        <input type="hidden" name="myMove" id="myMove"/>
    </form>
</div>

${sessionScope.niceButton}
${sessionScope.playerBoard}
${sessionScope.botBoard}
${sessionScope.finalMessage}

<script type="text/javascript">

function newGame() {
    $('[name=myMove]').val("NEW GAME");
    $("#moveForm").submit();
}

function move(clickedId)
    {
      var color = document.getElementById(clickedId).style.backgroundColor;
      if (color == "black" || color == "white") {
        document.getElementById("log").textContent = "Bad move";
      } else {
          $('[name=myMove]').val("B" + clickedId);
          $("#moveForm").submit();
      }
    }

function endgame(message) {
      $('[name=myMove]').val(message);
      $("#moveForm").submit();
}



</script>
</body>

</html>
