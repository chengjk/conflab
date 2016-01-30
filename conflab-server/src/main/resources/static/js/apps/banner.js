define(['jquery','_','Data','app','mockdata'], function ($,_,Data,App) {
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
            if(!_.isEmpty(key)){
                App.loadApps(key);
            }
        }
    }
});