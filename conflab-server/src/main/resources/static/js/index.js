define(['jquery',"app",'banner','Data',"etab"],function($,app,banner,Data){
    app.init();
    banner.init();
    //获取url中的key参数
    function getUrlParam(name) {
         var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
         var r = window.location.search.substr(1).match(reg);
         if(r!=null)return  unescape(r[2]); return null;
    }
    Data.urlKey=getUrlParam();
});