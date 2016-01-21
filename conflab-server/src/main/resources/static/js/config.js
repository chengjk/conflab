define(['jquery','_','config','mockdata'], function ($,_,conf){
var self;
    return {
        init:function(){
            self=this;
        },
        open:function(id){
            var url = "/conf/group/"+id;
            $.getJSON(url, function (datas) {
                console.log(datas);
                $.get("temp/conftab.html",function(temp){
                    console.log(temp);
                    var t = _.template(temp,{ 'variable': 'datas'});
                    var lr=t(datas);
                    $("#tabConfig tbody").empty().append(lr);
                    self.initView();
                });
            });
        },
        initView:function(){
             $("#tabConfig").parent().removeClass("hidden");
             $("#tabGroup").parent().addClass("hidden");

             $("#tabConfig tr").on("click",function(e){
                alert($(this).data("groupid"));
             })
        }
    }
});