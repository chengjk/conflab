define(['jquery','_','group','Data','breadcrumb','mockdata'], function ($,_,group,Data,Breadcrumb) {
    var self;
    return {
        init: function () {
            self=this;
            group.init();
            this.loadApps();
        },
        loadApps: function (key) {
            console.log("loadApp");
            if(key==undefined)key=Data.urlKey;
            var url = "/app/key/"+key;
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
                $("#tabConfig").parent().addClass("hidden");
                $("#tabGroup").parent().removeClass("hidden");
                var app={};
                app.id=$(this).data("appid");
                app.name=$(this).data("appname");
                Data.setApp(app);
                Breadcrumb.update();
                self.open(app.id);
            });
            $(".btn-toolbar button").click(function (e) {
                if ($(this).hasClass("btn-add")) {
                   var name= prompt("Add App","name");
                   alert(name);
                }
                if ($(this).hasClass("btn-copy")) {
                    self.copy();
                }
                if ($(this).hasClass("btn-edit")) {
                    self.edit();
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
            group.loadGroups(appId);
        },
        push:function(appId){
            if(confirm("推送可能导致对应应用重启，确认要推送配置吗？")){
                $.post("/app/push",{'appId':appId},function(e){
                    console.log("push success,"+appId);
                })
            }
        },
        add:function(){
            alert("add");
        },
        copy:function(){
            alert("copy");
        },
        edit:function(){
            alert("edit");
        },
        pushAll:function(){
            if(confirm("推送可能导致对应应用重启，确认要推送全部配置吗？")){
                $.post("/app/pushAll",{'key':""},function(e){
                     alert("ok");
                 })
            }
        },
        del:function(){
            if(Data.getApp().id==null){
                alert("请选择要删除的应用。");
                return false;
            }
            if(confirm("删除不可恢复，确认要删除吗？")){
                $.post("/app/del",{'appId':Data.getApp().id},function(e){
                    $(".list-group .active").remove();
                })
            }
        }
    };
});