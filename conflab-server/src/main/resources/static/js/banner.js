define(['jquery','Data','mockdata'], function ($,Data) {
    var self;
    return{
        init:function(){
            self=this;
            this.initView();
        },
        initView:function(){
            $("header input").keypress(function(e){
                if(e.keyCode==13){
                   self.search($("header input").val());
                }
            })
        },
        search:function(key){
            //todo search app
            //todo search conf
            alert("search  "+key);
        }
    }
});