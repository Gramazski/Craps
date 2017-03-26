/**
 * Created by gs on 19.03.2017.
 */
var commonModule = (function () {
    return {
        closeMessageModal : function () {
            $('.close').click();
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
        setActiveMenu : function (activeItem) {
            $('.site-menu').removeClass("active");
            $(activeItem).addClass("active");
        }
    }
})();