define(['jquery', '_'], function ($, _) {
    return {
        init: function () {
            var content = '<div id="alertList" class="col-md-12"> <div class="col-md-6 pull-right"> </div> </div>';
            $("body").append(content);
        },
        success: function (msg) {
            this.show("success", msg);
        },
        info: function (msg) {
            this.show("info", msg);
        },
        warning: function (msg) {
            this.show("warning", msg);
        },
        danger: function (msg) {
            this.show("danger", msg);
        },
        error: function (msg) {
            this.danger(msg);
        },
        show: function (level="info", msg='') {
            let levels = ["success", "info", "warning", "danger"];
            let clazz;
            if (_.contains(levels, level)) {
                clazz = "alert alert-" + level;
            }else {
                let errMsg = "msg level error.must one of below :success,info,warning，danger";
                console.log();
                clazz= "alert alert-danger";
                msg = errMsg;
            }

            let d = '<div class="' + clazz + '"> <strong>' + _.toUpper(level) + ': </strong>' + msg + '</div>';
            $("#alertList .pull-right").append(d);
            $("#alertList .pull-right").find("div:last").delay(3000).slideUp(200).queue (function () {
                $("#alertList .pull-right").find("div[style]").remove();
            });
        },
        test: function () {
            this.show("info", "info msg");
            this.show("danger", "danger msg");
            this.show("success", "success msg");
            this.show("warning", "warning msg");
        }
    }
});