define(['jquery', '_', 'config', 'Data', 'breadcrumb', 'msg', 'mockdata'], function ($, _, config, Data, Breadcrumb, msg) {
    var self;
    return {
        init: function () {
            self = this;
            config.init();
            self.initView();
        },
        loadGroups: function (appName) {
            $("#tabGroup tbody").empty();
            console.log("loadGroup");
            var url = "/group/"+appName+"/all";
            $.getJSON(url, function (datas) {
                $.get("temp/grouptab.html", function (temp) {
                    var t = _.template(temp, {'variable': 'datas'});
                    var lr = t(datas);
                    $("#tabGroup tbody").html(lr);
                });
            });
        },
        initView: function () {
            $("#tabGroup tbody").html("");
            console.log("group init")
            $("#tabGroup").delegate("tbody tr", "click", function () {
                console.log($(this));
                var g = {};
                g.id = $(this).data("id");
                g.name = $(this).data("name")
                g.desc = $(this).data("desc")
                Data.setGroup(g);
                self.open(g.name);
                Breadcrumb.update();
            });
            $("#tabGroup").delegate("tr button", "click", function (e) {
                e.stopPropagation();
                var text = $(this).text();
                var tr = $(this).closest("tr");
                var g = {};
                g.name = tr.data("name");
                g.desc = tr.data("desc");
                Data.setGroup(g);
                if ("edit" == text) {
                    $("#addAppModal h4").html("Edit group");
                    $("#addAppModal input[name='name']").val(Data.getGroup().name);
                    $("#addAppModal input[name='desc']").val(Data.getGroup().desc);
                    $("#addAppModal .btn-primary").html("Submit").off("click").click(function () {
                        var name = $("#addAppModal input[name='name']").val();
                        if (_.isEmpty(name)) {
                            $("#addAppModal input[name='name']").closest(".form-group").addClass("has-error");
                        } else {
                            $("#addAppModal input").closest(".form-group").removeClass("has-error");
                            var desc = $("#addAppModal input[name='desc']").val();
                            //clear input
                            $("#addAppModal input").val("");
                            self.edit(name, desc);
                            $("#addAppModal").modal("hide");
                        }
                    });
                    $("#addAppModal").modal();
                } else if ("del" == text) {
                    self.del(tr.data("name"));
                }
            });
            $("#tabGroup thead button").click(function () {
                if (Data.getApp() == null) {
                    msg.info("请先选择一个应用。");
                    return;
                }
                $("#addAppModal h4").html("Add group");
                $("#addAppModal input").val("");
                $("#addAppModal .btn-primary").html("Add").off("click").click(function () {
                    var name = $("#addAppModal input[name='name']").val();
                    if (_.isEmpty(name)) {
                        $("#addAppModal input[name='name']").closest(".form-group").addClass("has-error");
                    } else {
                        $("#addAppModal input").closest(".form-group").removeClass("has-error");
                        var desc = $("#addAppModal input[name='desc']").val();
                        //clear input
                        $("#addAppModal input").val("");
                        self.add(name, desc);
                        $("#addAppModal").modal("hide");
                    }
                });
                $("#addAppModal").modal();
            })
            //add form
            var form = $("#tabGroup").next(".form-inline");
            form.find("button").off("click").click(function () {
                self.add(form);
            })
        },
        clear:function(){
            $("#tabGroup tbody").html("");
        },
        open: function (groupName) {
            config.loadConfigs(groupName);
        },
        add: function (name, desc) {
            if (Data.getApp() == null) {
                msg.info("请先选择一个应用。");
            } else {
                $.ajax({
                    url: "/group/add",
                    data: {'app': Data.getApp().name, 'name': name, 'desc': desc},
                    success: function () {
                        msg.success("add group success!");
                        self.loadGroups(Data.getApp().name);
                    },
                    error: function (req, status, err) {
                        msg.error(req.responseJSON.message);
                    }
                })
            }
        },
        edit: function (name, desc) {
            $.ajax({
                url: "/group/update",
                data: {'srcName': Data.getGroup().name, 'app': Data.getApp().name, 'name': name, 'desc': desc},
                success: function () {
                    msg.success("edit group success!");
                    self.loadGroups(Data.getApp().id);
                },
                error: function (req, status, err) {
                    msg.error(req.responseJSON.message);
                }
            })
        },
        del: function (groupName) {
            if (confirm("删除不可恢复，确认要删除吗？" + groupName)) {
                $.post("/group/del", {'app': Data.getApp().name,'group':groupName}, function (e) {
                    $("#tabGroup tr[data-name='" + groupName + "']").remove();
                });
            }
        }
    }
});
