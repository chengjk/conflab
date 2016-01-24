define(function(){

    //当前选中的 app，group，config。
    //同时只能有一个。又因为关联关系，采用get set 方法。
    var appId,groupId,confId;
    return{
        urlKey:null, //url 中的key参数
        getAppId:function(){
            return appId
        },
        setAppId:function(id){
           appId=id;
           groupId=null;
           confId=null;
        },
        setGroupId:function(id){
           groupId=id;
           confId=null;
        },
        getGroupId:function(){
            return groupId;
        },
        setConfId:function(id){
           confId=id;
        },
        getConfId:function(){
            return confId;
        }
    }
})