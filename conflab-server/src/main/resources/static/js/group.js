define(['jquery','_','config','mockdata'], function ($,_,config) {
    var self;
    return {
        init: function () {
            self = this;
        },
        loadData:function(appId){
            console.log("loadGroup");
            var url = "/group/app/"+appId;
            $.getJSON(url, function (datas) {
                console.log(datas);
                $.get("temp/grouptab.html",function(temp){
                    console.log(temp);
                    var t = _.template(temp,{ 'variable': 'datas'});
                    var lr=t(datas);
                    $("#tabGroup tbody").empty().append(lr);
                    self.initView();
                });
            });
        },
        initView:function(){
             $("#tabConfig").parent().addClass("hidden");
             $("#tabGroup").parent().removeClass("hidden");
             $("#tabGroup tr").on("click",function(e){
                self.open($(this).data("id"))
             });
        },
        open:function(id){
            config.init();
            config.open(id);
        }
    }
});