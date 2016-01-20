define(['jquery','_','mockdata'], function ($,_) {
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
                    $("#tabGroup tbody").append(lr);
                    //self.initView();
                });
            });
        }
    }
});