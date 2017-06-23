$(document).ready(function() {
    $("#newGame").click( function() {
        var name = $("#username").val();
        if(name!==""){
            $.ajax({
                url: "/api/tictactoe/game/newgame",
                dataType: "json",
                method: "POST",
                data: {player:name},
                success: function(result) {
                    window.location.href = "/game/?id="+result.id+"&name="+name;
                }
            });
        }
    });

    $(".connect").click( function() {
        var name = $("#username").val();
        var gameid = $(this).attr('id');
        if(name!==""){
            $.ajax({
                url: "/api/tictactoe/game/"+gameid+"/connect",
                dataType: "json",
                method: "POST",
                data: {
                    id:gameid,
                    player:name
                },
                success: function(result) {
                    window.location.href = "/game/?id="+result.id+"&name="+name;
                }
            });
        }
    });

});
