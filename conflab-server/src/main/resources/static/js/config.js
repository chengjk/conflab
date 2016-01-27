define(['jquery','_','config','Data','mockdata'], function ($,_,conf,Data){
    var self;
    return {
        init:function(){
            self=this;
            self.initView();
        },
        loadConfigs:function(groupId){
            $("#tabConfig tbody").empty()
            $("#tabConfig").parent().removeClass("hidden");
            $("#tabGroup").parent().addClass("hidden");
            var url = "/conf/group/"+groupId;
            $.getJSON(url, function (datas) {
                console.log(datas);
                $.get("temp/conftab.html",function(temp){
                    console.log(temp);
                    var t = _.template(temp,{ 'variable': 'datas'});
                    var lr=t(datas);
                    $("#tabConfig tbody").html(lr);
                });
            });
        },
        initView:function(){
            $("#tabConfig").editableTableWidget();
            $("#tabConfig").delegate("tr","click",function(){
                var c={};
                c.id=$(this).data("id");
                Data.setConfig(c);
                alert(c.id)
            });
            $("#tabConfig").delegate("button","click",function(e){
                e.stopPropagation();
                var tr=$(this).closest("tr");
                self.del(tr);
            });
            var form=$("#tabConfig").next(".form-inline");

            form.find("button").off("click").click(function(){
                self.add(form);
            })
        },
        add:function(form){
            console.log("add config");
            if (Data.getApp() != null && Data.getGroup()!=null) {
                form.find("input[name=appId]").val(Data.getApp().id);
                form.find("input[name=groupId]").val(Data.getGroup().id);
                $.post("/conf/add",form.serialize(),function(e){
                    alert("ok");
                    form.reset();
                })
            }else {
                alert("先选择应用和组。");
            }
        },
        del:function(tr){
             var confId=tr.data("id");
             if(confirm("删除不可恢复，确认要删除吗？"+confId)){
                 $.post("/conf/del",{'id':confId},function(e){
                    tr.remove();
                 })
             }
        }
    }
});