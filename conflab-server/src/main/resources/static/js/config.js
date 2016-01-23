define(['jquery','_','config','Data','mockdata'], function ($,_,conf,Data){
    var self;
    return {
        init:function(){
            self=this;
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
                    self.initView();
                });
            });
        },
        initView:function(){
            $("#tabConfig").editableTableWidget();
            $("#tabConfig tr").click(function(){
               alert($(this).data("id"))
            });
            $("#tabConfig button").click(function(e){
                e.stopPropagation();
                var tr=$(this).closest("tr");
                self.del(tr);
            })
            var form=$("#tabConfig").next(".form-inline");
            form.find("input[name=appId]").val(Data.appId);
            form.find("input[name=groupId]").val(Data.groupId);
            form.find("button").off("click").click(function(){
                self.add(form);
            })
        },
        add:function(form){
            console.log("add config")
            $.post("/conf/add",form.serialize(),function(e){
                alert("ok");
                form.reset();
            })
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