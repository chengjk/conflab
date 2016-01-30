define(['jquery','_'],function($,_){
return{
    init : function(){
        var content='<div id="alertList" class="col-md-12"> <div class="col-md-6 pull-right"> </div> </div>';
        $("body").append(content);
    },
    success:function(msg){
        this.show("success",msg);
    },
    info:function(msg){
        this.show("info",msg);
    },
    warning:function(msg){
        this.show("warning",msg);
    },
    danger:function(msg){
        this.show("danger",msg);
    },
    error:function(msg){
        this.danger(msg);
    },
    show : function(level,msg){
        if(_.isEmpty(level)||_.isEmpty(msg)){
            console.log("error, level or msg is empty.");
            return;
        }
        var clazz;
        if(level=="success"||level=="info"||level=="warning"||level=="danger"){
            clazz="alert alert-"+level;
        }
        var d='<div class="'+clazz+'"> <strong>'+_.toUpper(level)+': </strong>'+msg+'</div>';
        $("#alertList .pull-right").append(d);
        $("#alertList .pull-right").find("div:last").delay(3000).slideUp(200).queue (function (){
            $("#alertList .pull-right").find("div[style]").remove();
        });
    },
    test:function(){
        this.show("info","info msg");
        this.show("danger","danger msg");
        this.show("success","success msg");
        this.show("warning","warning msg");
    }
}
});