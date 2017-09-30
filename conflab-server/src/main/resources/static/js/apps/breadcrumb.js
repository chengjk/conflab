define(['jquery', 'Data', '../apps/app','../apps/group'], function ($, Data, App,Group) {
    return {
        update: function () {
            var node = $(".breadcrumb");
            node.empty().append('<li class="nav-home">Home</li>');
            var app = Data.getApp();
            var group = Data.getGroup();
            if (app != null) {
                node.append('<li class="nav-app"><a href="#">' + app.name + '</a></li>');
            }
            if (group != null) {
                node.append('<li class="nav-group"><a href="#">' + group.name + '</a></li>');
            }
            node.find("li").last().addClass("active");
            this.bindEvent();
        },
        bindEvent: function () {
            $(".breadcrumb li.nav-app").click(function (e) {
                $("#tabConfig").parent().addClass("hidden");
                $("#tabGroup").parent().removeClass("hidden");
                Group.loadGroups(Data.getApp().id)
            });
        }
    }
})