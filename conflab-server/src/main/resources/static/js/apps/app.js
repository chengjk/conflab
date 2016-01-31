define(['jquery','_','group','Data','breadcrumb','msg','mockdata'], function ($,_,group,Data,Breadcrumb,msg) {
    var self;
    return {
        init: function () {
            self=this;
            group.init();

            self.initView();
            self.loadApps();
        },
        loadApps: function (key) {
            console.log("loadApp");
            if(key==undefined)key=Data.urlKey;
            var url = "/app/key/"+key;
            $.getJSON(url, function (apps) {
                $.get("temp/applist.html",function(temp){
                    var t = _.template(temp,{ 'variable': 'apps'});
                    var lr=t(apps);
                    $(".list-group").html(lr);
                });
            });
        },
        initView: function () {
            console.log("app init");
            $(".list-group").delegate("a","click",function(e){
                e.stopPropagation();
                var appId=$(this).parent().data("appid");
                self.push(appId);
            });
            $(".list-group").delegate(".list-group-item","click",function(){
                $(".list-group li").removeClass("active");
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
                    $("#addAppModal h4").html("Add app");
                    $("#addAppModal .btn-primary").html("Add").off("click").click(function(){
                        var name=$("#addAppModal input[name='name']").val();
                        if(_.isEmpty(name)){
                            $("#addAppModal input[name='name']").closest(".form-group").addClass("has-error");
                        }else{
                            $("#addAppModal input").closest(".form-group").removeClass("has-error");
                            var desc=$("#addAppModal input[name='desc']").val();
                            //clear input
                            $("#addAppModal input").val("");
                            self.add(name,desc);
                            $("#addAppModal").modal("hide");
                        }

                    });
                    $("#addAppModal").modal();
                }
                if ($(this).hasClass("btn-copy")) {
                    if(Data.getApp()==null){
                        msg.info("请选中要复制的对象!");
                        return;
                    }
                    var name= prompt("Input destination app name","name");
                    self.copy(name);
                }
                if ($(this).hasClass("btn-edit")) {
                    if(Data.getApp()==null){
                       msg.error("No valid app!");
                       return;
                    }
                    $("#addAppModal h4").html("Edit app");
                    $("#addAppModal .btn-primary").html("Submit").off("click").click(function(){
                        var name=$("#addAppModal input[name='name']").val();
                        if(_.isEmpty(name)){
                            $("#addAppModal input[name='name']").closest(".form-group").addClass("has-error");
                        }else{
                            $("#addAppModal input").closest(".form-group").removeClass("has-error");
                            var desc=$("#addAppModal input[name='desc']").val();
                            //clear input
                            $("#addAppModal input").val("");
                            self.edit(name,desc);
                            $("#addAppModal").modal('hide');
                        }

                    });
                    $("#addAppModal").modal();
                }
                if ($(this).hasClass("btn-delete")) {
                    if(Data.getApp()==null){
                       msg.info("请选择要删除的应用。");
                       return;
                    }
                    self.del(Data.getApp());
                }
                if ($(this).hasClass("btn-push")) {
                    self.pushAll();
                }
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
        add:function(name,desc){
            console.log("add");
            if(_.isEmpty(name)){
                msg.warning("Invalid name,try again!");
            }else{
                $.post("/app/add",{'name':name,'descp':desc},function(e){
                    msg.success("add app success!");
                })
            }
        },
        copy:function(name){
            console.log("cp")
            if(_.isEmpty(name)){
                msg.info("Invalid name,try again!");
            }else{
                $.post("/app/cp",{'srcId':Data.getApp().id,'tarName':name},function(e){
                    msg.success("copy app success!");
                })
            }

        },
        edit:function(name,desc){
           console.log("edit");
           if(_.isEmpty(name)){
               msg.info("Invalid name,try again!");
           }else{
               $.post("/app/update",{'id':Data.getApp().id,'name':name,'descp':desc},function(e){
                   msg.success("update app success!");
               })
           }
        },
        pushAll:function(){
            if(confirm("推送可能导致对应应用重启，确认要推送全部配置吗？")){
                $.post("/app/pushAll",{'key':""},function(e){
                     msg.success("push all success!");
                 })
            }
        },
        del:function(app){
            if(confirm("删除不可恢复，确认要删除吗？")){
                $.post("/app/del",{'appId':app.id},function(e){
                    $(".list-group .active").remove();
                })
            }
        }
    };
});