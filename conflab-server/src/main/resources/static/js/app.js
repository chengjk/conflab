define(['jquery','_','group','mockdata'], function ($,_,group) {
    var self;
    return {
        init: function () {
            self=this;
            group.init();
            this.loadData();
        },
        loadData: function () {
            console.log("loadData");
            var url = "/app/all";
            $.getJSON(url, function (apps) {
                console.log(apps);
                $.get("temp/applist.html",function(temp){
                    var t = _.template(temp,{ 'variable': 'apps'});
                    var lr=t(apps);
                    $(".list-group").html(lr);
                    self.initView();
                });
            });
        },
        initView: function () {
            console.log("app init");
            $(".list-group a").click( function (e) {
                alert("push item");
            });
            $(".list-group-item").click( function (e) {
                $(this).parent().find(".list-group-item").removeClass("active");
                $(this).addClass("active");
                self.open($(this).data("appid"));
            });
            $(".btn-toolbar button").click(function (e) {
                if ($(this).hasClass("btn-add")) {
                    alert("add");
                }
                if ($(this).hasClass("btn-copy")) {
                    alert("copy");
                }
                if ($(this).hasClass("btn-edit")) {
                    alert("edit");
                }
                if ($(this).hasClass("btn-delete")) {
                    alert("delete");
                }
                if ($(this).hasClass("btn-push")) {
                    alert("push all");
                }
                console.log($(this));
            });
            console.log("app init ok")
        },
        open:function(appId){
            group.loadData(appId);
        },
        add:function(){

        }
    };
});