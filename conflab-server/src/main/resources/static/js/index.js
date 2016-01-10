define(['jquery',"app"],function($,app){
    require(["app","etab"],function(app){
        $("#tabConfig").editableTableWidget();
    })
    app.init();
});