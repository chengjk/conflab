define(['jquery','Data'],function($,Data){
return {
    update:function(){
        var node=$(".breadcrumb");
        node.empty().append('<li>Home</li>');;
        var app=Data.getApp();
        var group=Data.getGroup();
        if(app!=null){
            node.append('<li><a href="#">'+app.name+'</a></li>');
        }
        if(group!=null){
            node.append('<li><a href="#">'+group.name+'</a></li>');
        }
        node.find("li").last().addClass("active");
    }
}
})