define(['jquery','app','banner','Data','breadcrumb','msg',"modal"],function($,app,banner,Data,breadcrumb,msg){
    app.init();
    banner.init();
    msg.init();
    breadcrumb.update();
    //获取url中的key参数
    function getUrlParam(name) {
         var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
         var r = window.location.search.substr(1).match(reg);
         if(r!=null)return  unescape(r[2]); return null;
    }
    Data.urlKey=getUrlParam();
    $(".modal-dialog input").keypress(function(e){
        if(e.keyCode==13){
           $(".modal-dialog .btn-primary").trigger("click");
        }
    });
});