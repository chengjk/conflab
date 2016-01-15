define(['jquery','mockdata'], function ($) {
    return {
        init: function () {
            this.initView();
            this.loadData();
        },
        loadData: function () {
            console.log("loadData");
            var url = "/app/all";
            $.getJSON(url, function (data) {
                $.each(data, function (i, item) {
                    console.log(item);
                })
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