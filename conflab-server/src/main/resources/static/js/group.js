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
        }
    }
});