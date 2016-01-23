define(['jquery','_','group','Data','mockdata'], function ($,_,group,Data) {
    var self;
    return {
        init: function () {
            self=this;
            group.init();
            this.loadApps();
        },
        loadApps: function () {
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
            $(".list-group a").on("click", function (e) {
                e.stopPropagation();
                var appId=$(this).parent().data("appid");
                self.push(appId);
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
                    self.del();
                }
                if ($(this).hasClass("btn-push")) {
                    self.pushAll();
                }
                console.log($(this));
            });
            console.log("app init ok")
        },
        open:function(appId){
            group.loadData(appId);
        },
        add:function(){

        },
        push:function(appId){
            if(confirm("推送可能导致对应应用重启，确认要推送配置吗？")){
                $.post("/app/push",{'appId':appId},function(e){
                    alert("ok");
                })
            }
        },
        pushAll:function(){
            if(confirm("推送可能导致对应应用重启，确认要推送全部配置吗？")){
                $.post("/app/pushAll",{'key':""},function(e){
                     alert("ok");
                 })
            }
        },
        del:function(){
            if(Data.appId==""){
                alert("请选择要删除的应用。");
                return false;
            }
            if(confirm("删除不可恢复，确认要删除吗？")){
                $.post("/app/del",{'appId':Data.appId},function(e){
                    $(".list-group .active").remove();
                })
            }
        }
    };
});