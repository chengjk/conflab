define(['jquery','_','config','Data','mockdata'], function ($,_,conf,Data){
    var self;
    return {
        init:function(){
            self=this;
        },
        open:function(groupId){
            $("#tabConfig tbody").empty()
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
             $("#tabConfig").parent().removeClass("hidden");
             $("#tabGroup").parent().addClass("hidden");
             $("#tabConfig").editableTableWidget();
             $("#tabConfig tr").click(function(){
                alert($(this).data("id"))
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
        }
    }
});