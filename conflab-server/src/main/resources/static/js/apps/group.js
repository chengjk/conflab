define(['jquery','_','config','Data','breadcrumb','msg','mockdata'], function ($,_,config,Data,Breadcrumb,msg) {
    var self;
    return {
        init: function () {
            self = this;
            config.init();
            self.initView();
        },
        loadGroups:function(appId){
            $("#tabGroup tbody").empty();
            console.log("loadGroup");
            var url = "/group/app/"+appId;
            $.getJSON(url, function (datas) {
                $.get("temp/grouptab.html",function(temp){
                    console.log(temp);
                    var t = _.template(temp,{ 'variable': 'datas'});
                    var lr=t(datas);
                    $("#tabGroup tbody").html(lr);
                });
            });
        },
        initView:function(){
            console.log("group init")
            $("#tabGroup").delegate("tbody tr","click",function(){
                console.log($(this));
                var g={};
                g.id=$(this).data("id");
                g.name=$(this).data("name")
                Data.setGroup(g);
                self.open(g.id);
                Breadcrumb.update();
            });
            $("#tabGroup").delegate("tr button","click",function(e){
                e.stopPropagation();
                var text=$(this).text();
                var tr=$(this).closest("tr");
                var g={};
                g.id=tr.data("id");
                g.name=tr.data("name")
                Data.setGroup(g);
                if("edit"==text){
                    $("#addAppModal h4").html("Edit group");
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
                            $("#addAppModal").modal("hide");
                        }
                    });
                    $("#addAppModal").modal();
                }else if("del"==text){
                    self.del(tr.data("id"));
                }
            });
            $("#tabGroup thead button").click(function(){
                if (Data.getApp() == null) {
                    msg.info("请先选择一个应用。");
                    return;
                }
                $("#addAppModal h4").html("Add group");
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
            })
            //add form
            var form=$("#tabGroup").next(".form-inline");
            form.find("button").off("click").click(function(){
                self.add(form);
            })
        },
        open:function(groupId){
            config.loadConfigs(groupId);
        },
        add:function(name,desc){
            if (Data.getApp() == null) {
                msg.info("请先选择一个应用。");
            }else {
                $.post("/group/add",{'appId':Data.getApp().id,'name':name,'descp':desc},function(e){
                    msg.success("add group success!");
                })
            }
        },
        edit:function(name,desc){
                $.post("/group/update",
                {'id':Data.getGroup().id, 'appId':Data.getApp().id,'name':name,'descp':desc}
                ,function(e){
                    msg.success("edit group success!");
                });
        },
        del:function(groupId){
            if(confirm("删除不可恢复，确认要删除吗？"+groupId)){
                $.post("/group/del",{'id':groupId},function(e){
                    $("#tabGroup tr[data-id='"+groupId+"']").remove();
                });
            }
        }
    }
});