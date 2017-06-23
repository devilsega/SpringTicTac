var player;
var receivedData;
var interval;

$(document).ready(function() {
    checkStatus();
    player=getUrlParameter("name");

    $(".grid").click( function() {
        $.ajax({
            url: "/api/tictactoe/game/"+getUrlParameter("id")+"/move",
            dataType: "json",
            method: "POST",
            data: {
                move: $(this).attr('id'),
                player:player
            },
            success: function() {
                checkStatus();
            }
        });
    });

    $("#exit").click( function() {
        $.ajax({
            url: "/api/tictactoe/game/"+getUrlParameter("id")+"/endgame",
            dataType: "json",
            method: "POST",
            success: function() {
                clearInterval(interval);
                window.location.href = "/";
            }
        });
    });

    //Game grid update timer
    interval = setInterval(function() {
        checkStatus();
    }, 3000)
});

function checkStatus() {
    $.ajax({
        url: "/api/tictactoe/game/"+getUrlParameter("id")+"/currentstate",
        dataType: "json",
        method: "GET",
        success: function(result) {
            receivedData = result;
            changeGrid(result);
            if (receivedData.winner===null){
                setTextWhoseTurn();
            }
            if (receivedData.winner!==null){
                setGameWinner();
                clearInterval(interval);
            }
            else if (receivedData.closed===true){
                setGameIsClosed();
                clearInterval(interval);
            }
        },
        statusCode:{
            400: function () {
                setGameNotPending();
            }
        }

    });
}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

function setTextWhoseTurn() {
    var textPanel = $(".info-text");
    if (receivedData.currentPlayer === null){
        textPanel.text("Ожидайте подключения второго игрока");
    }
    else{
        textPanel.text("Ходит: "+receivedData.currentPlayer.name);
    }
}

function setGameNotPending() {
    $(".info-text").text("Нет подключения");
}

function setGameWinner() {
    $(".info-text").text("Победитель: "+receivedData.winner.name+" !!!");
}

function setGameIsClosed() {
    $(".info-text").text("Ваш оппонент закрыл игру");
}

function changeGrid(receivedResult) {
    var grid;
    grid = {a1:receivedResult.a1, a2:receivedResult.a2, a3:receivedResult.a3,
        b1:receivedResult.b1, b2:receivedResult.b2, b3:receivedResult.b3,
        c1:receivedResult.c1, c2:receivedResult.c2, c3:receivedResult.c3};

    $.each(grid,function(k,v){
        if (v!==null){
            if (v!==player){
                changeCell(k,"red");
            }
            if (v===player){
                changeCell(k,"blue");
            }
        }
    });
}

function changeCell(address,color) {
    var cell = $("#"+address);
    cell.removeClass("neutral");
    if (color==="blue"){
        cell.addClass("blue-x");
    }
    if (color==="red"){
        cell.addClass("red-0");
    }
}