define(function(){

    //当前选中的 app，group，config。
    //同时只能有一个。又因为关联关系，采用get set 方法。
    var app=null;
    var group=null;
    var config=null;
    return{
        urlKey:null, //url 中的key参数
        getApp:function(){
            return app;
        },
        setApp:function(o){
           app=o;
           group=null;
           config=null;
        },
        setGroup:function(o){
           group=o;
           config=null;
        },
        getGroup:function(){
            return group;
        },
        setConfig:function(o){
           config=o;
        },
        getConfig:function(){
            return config;
        }
    }
})