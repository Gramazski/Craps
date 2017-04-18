/**
 * Created by gs on 19.03.2017.
 */
var commonModule = (function () {
    return {
        showAnimate : function () {
            var target = document.getElementById('gifField');
            var field = document.getElementById('imgField');
            field.style.visibility = 'hidden';
            target.src = "assets/img/game/field.gif";
            target.style.visibility = 'visible';
        },
        hideAnimate : function () {
            var target = document.getElementById('gifField');
            target.src = "";
            target.style.visibility = 'hidden';
            var field = document.getElementById('imgField');
            field.style.visibility = 'visible';
        },
        showThrowingResult : function () {
            $('#throwingResult').modal('show');
        },
        closeMessageModal : function () {
            $('.close').click();
        },
        clearMessageModal : function () {
            var target = document.getElementById('title');
            target.value = "";
            target = document.getElementById('username');
            target.value = "";
            target = document.getElementById('body');
            target.value = "";
        },
        setActiveInMessageList : function () {
            $('#inMessages').addClass("active");
            $('#outMessages').removeClass("active");
        },
        setActiveOutMessageList : function () {
            $('#outMessages').addClass("active");
            $('#inMessages').removeClass("active");
        },
        updateMessagesList : function () {
            if ($('#inMessages').hasClass("active")){
                $('#inMessages').click();
            }
            else {
                $('#outMessages').click();
            }
        },
        updateChat : function () {
            $('#gameId').click();
        },
        updateGame : function () {
            $('#bet').click();
        },
        resetFormIn : function (formName) {
            $(formName)[0].reset();
        },
        getFieldPos : function () {
            var fieldPos = {};
            var target = document.getElementById('imgField');
            fieldPos.width = target.width;
            fieldPos.height = target.height;

            return fieldPos;
        },
        setBet : function (x, y, width, type) {
            var parent = document.getElementById('imgTable');
            var target = document.createElement("img");
            target.src ="assets/img/game/bet.jpg";
            target.id = type;
            target.style.position = "absolute";
            target.style.left = x + 'px';
            target.style.top =y + 'px';
            target.style.width = width + 'px';

            parent.appendChild(target);
        },
        removeBet : function (type) {
            var parent = document.getElementById('imgTable');
            var target = document.getElementById(type);

            parent.removeChild(target);
        },
        setAreaWidth : function (areas, bets) {
            var field = document.getElementById('imgField');
            var i;
            for (i = 0; i < areas.length; i++){
                var newCoords = [];
                for (var y = 0; y < areas[i].coordsSt.length; y++){
                    if (y % 2 == 0){
                        newCoords[y] = Math.round( areas[i].coordsSt[y] * field.width);
                    }
                    else {
                        newCoords[y] = Math.round( areas[i].coordsSt[y] * field.height);
                    }
                }

                areas[i].coords = newCoords;
                areas[i].width = areas[i].widthSt * field.width;
            }

            for (i = 0; i < bets.length; i++){
                var target = document.getElementById(bets[i].type);
                target.style.left = bets[i].position.x * field.width + 'px';
                target.style.top =bets[i].position.y * field.height + 'px';
                target.style.width = bets[i].position.width * field.width + 'px';
            }
        }
    }
})();