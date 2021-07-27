var gameId;
var playerTurn;

var play = function (pitId) {
    if (gameId === undefined || (playerTurn === 1 && pitId < 7) || (playerTurn === 2 && pitId > 7)) {
        if ($("div#pit" + pitId).text() === '0') {
            return;
        }
        $.ajax({
                url: "/play/" + pitId + "/" + (gameId !== undefined ? gameId : ""),
                type: 'PUT',
                success: function (data) {
                    gameId = data.gameId;
                    playerTurn = data.playerTurn;
                    for (var i = 0; i < data.pits.length; ++i) {
                        var pitElementId = "div#pit" + data.pits[i].id;
                        $(pitElementId).text(data.pits[i].stoneCount);

                        if (data.pits[i].id === 7 || data.pits[i].id === 14) {
                            continue;
                        }
                        if (data.pits[i].stoneCount === 0) {
                            $(pitElementId).parent().removeClass("man-box-small-shadow");
                            $(pitElementId).parent().removeClass("man-box-orange-small-shadow");
                            continue;
                        }
                        if (playerTurn === 1 && data.pits[i].id > 7) {
                            $(pitElementId).parent().removeClass("man-box-orange-small-shadow");
                            continue;
                        }
                        if (playerTurn === 2 && data.pits[i].id < 7) {
                            $(pitElementId).parent().removeClass("man-box-small-shadow");
                            continue;
                        }
                        if (playerTurn === 1) {
                            $(pitElementId).parent().addClass("man-box-small-shadow");
                        } else {
                            $(pitElementId).parent().addClass("man-box-orange-small-shadow");
                        }
                    }
                }
            }
        );
    }
};