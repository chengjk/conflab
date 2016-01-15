define(['jquery','_','mockdata'], function ($,_) {
    return {
        init: function () {
            this.initView();
            this.loadData();
        },
        loadData: function () {
            console.log("loadData");
            var url = "/app/all";
            $.getJSON(url, function (data) {
                var fStr = "<% _.forEach(data, function(item) { %><li><%- item.name %></li><% }); %>";
                var t = _.template(fStr);
                var lr=t({"data":data});
                console.log(lr);
                //$.each(data, function (i, item) {
                //    var str="'hello <%= name %>!'"
                //    var c=_.template(str);
                //
                //    var r=c(item);
                //    console.log(r);
                //})
            });
        },
        initView: function () {
            $(".list-group a").on("click", function (e) {
                alert("push item");
            });
            $(".btn-toolbar button").on("click", function (e) {
                if ($(this).hasClass("btn-add")) {
                    alert("add");
                }
                if ($(this).hasClass("btn-copy")) {
                    alert("copy");
                }
                if ($(this).hasClass("btn-edit")) {
                    alert("edit");
                }
                if ($(this).hasClass("btn-delete")) {
                    alert("delete");
                }
                if ($(this).hasClass("btn-push")) {
                    alert("push all");
                }
                console.log($(this));
            });
        }
    };
});