define(['jquery','_','config','Data','breadcrumb','mockdata'], function ($,_,config,Data,Breadcrumb) {
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
            $("#tabGroup").delegate("tr","click",function(){
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
                if("edit"==text){
                    self.edit(tr);
                }else if("del"==text){
                    self.del(tr);
                }
            });
            var form=$("#tabGroup").next(".form-inline");
            form.find("button").off("click").click(function(){
                self.add(form);
            })
        },
        open:function(groupId){
            config.loadConfigs(groupId);
        },
        add:function(form){
            if (Data.getApp() == null) {
                alert("请先选择一个应用。");
            }else {
                form.find("input[name=appId]").val(Data.getApp().id);
                $.post("/group/add",form.serialize(),function(e){
                    alert("ok");
                    form.reset();
                })
            }
        },
        edit:function(groupId){
            alert("eidt:"+groupId);
        },
        del:function(tr){
            var groupId=tr.data("id");
            if(confirm("删除不可恢复，确认要删除吗？"+groupId)){
                $.post("/group/del",{'id':groupId},function(e){
                     tr.remove();
                 })
            }
        }
    }
});