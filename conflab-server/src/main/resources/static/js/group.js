define(['jquery','_','config','Data','mockdata'], function ($,_,config,Data) {
    var self;
    return {
        init: function () {
            self = this;
        },
        loadData:function(appId){
            $("#tabGroup tbody").empty();
            console.log("loadGroup");
            Data.appId=appId;
            var url = "/group/app/"+appId;
            $.getJSON(url, function (datas) {
                $.get("temp/grouptab.html",function(temp){
                    console.log(temp);
                    var t = _.template(temp,{ 'variable': 'datas'});
                    var lr=t(datas);
                    $("#tabGroup tbody").html(lr);
                    self.initView();
                });
            });
        },
        initView:function(){
            console.log("group init")
            $("#tabConfig").parent().addClass("hidden");
            $("#tabGroup").parent().removeClass("hidden");

            $("#tabGroup tr").click(function(e){
                Data.groupId=$(this).data("id");
                self.open(Data.groupId)
            });
            $("#tabGroup tr button").click(function(e){
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
            form.find("input[name=appId]").val(Data.appId);
            form.find("button").off("click").click(function(){
                self.add(form);
            })
        },
        open:function(id){
            config.init();
            config.open(id);
        },
        add:function(form){
            $.post("/group/add",form.serialize(),function(e){
                alert("ok");
                form.reset();
            })
        },
        edit:function(groupId){
            alert("eidt:"+groupId);
        },
        del:function(tr){
            var groupId=tr.data("id");
            if(confirm("删除不可恢复，确认要删除吗？"+groupId)){
                $.post("/group/del",{'id':groupId},function(e){
                     alert("ok");
                     tr.remove();
                 })
            }
        }
    }
});