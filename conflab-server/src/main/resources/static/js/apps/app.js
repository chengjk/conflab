define(['jquery', '_', 'group', 'Data', 'breadcrumb', 'msg', 'mockdata'], function ($, _, group, Data, Breadcrumb, msg) {
    var self;
    return {
        init: function () {
            self = this;
            group.init();
            self.initView();
            self.loadApps();
        },
        loadApps: function (key) {
            console.log("loadApp");
            console.log(Data.urlKey);
            if (key == undefined)key = Data.urlKey;
            var url = "/app/key/" + key;

            $.getJSON(url, function (apps) {
                $.get("temp/applist.html", function (temp) {
                    var t = _.template(temp, {'variable': 'apps'});
                    var lr = t(apps);
                    $(".list-group").html(lr);
                });
            });
        },
        initView: function () {
            console.log("app init");
            $(".list-group").delegate("a.pull-right", "click", function (e) {
                e.stopPropagation();
                var appId = $(this).parent().data("appname");
                self.push(appId);
            });
            $(".list-group").delegate(".list-group-item", "click", function () {
                $(".list-group li").removeClass("active");
                $(this).addClass("active");
                $("#tabConfig").parent().addClass("hidden");
                $("#tabGroup").parent().removeClass("hidden");
                var app = {};
                app.name = $(this).data("appname");
                app.desc = $(this).data("desc");
                Data.setApp(app);
                Breadcrumb.update();
                self.open(app.name);
            });
            $(".btn-toolbar button").click(function (e) {
                if ($(this).hasClass("btn-add")) {
                    $("#addAppModal h4").html("Add app");
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
                }
                if ($(this).hasClass("btn-copy")) {
                    if (self.isValidApp()) {
                        var name = prompt("Input destination app name", "name");
                        self.copy(name);
                    }
                }
                if ($(this).hasClass("btn-edit")) {
                    if (!self.isValidApp()) return;
                    $("#addAppModal h4").html("Edit app");
                    $("#addAppModal input[name='name']").val(Data.getApp().name);
                    $("#addAppModal input[name='desc']").val(Data.getApp().desc);
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
                            $("#addAppModal").modal('hide');
                        }

                    });
                    $("#addAppModal").modal();
                }
                if ($(this).hasClass("btn-delete")) {
                    if (self.isValidApp()) {
                        self.del(Data.getApp());
                    }
                }
                if ($(this).hasClass("btn-push")) {
                    self.pushAll();
                }

                if ($(this).hasClass("btn-export")) {
                    if (self.isValidApp()) {
                        self.doExport()
                    }
                }
                if ($(this).hasClass("btn-import")) {
                    $("#modalImport").modal("show");
                }
            });
            $("#modalImport .modal-footer .btn-primary").off("click").on("click", function () {
                var json = $("#modalImport textarea").val();
                $("#modalImport").modal("hide");
                self.doImport(json);
            });

            console.log("app init ok")
        },
        open: function (appName) {
            group.loadGroups(appName);
        },
        push: function (appName) {
            if (confirm("推送可能导致对应应用重启，确认要推送配置吗？")) {
                $.post("/app/push", {'appName': appName}, function (e) {
                    console.log("push success," + appName);
                    msg.success("push " + appName + " success!");

                })
            }
        },
        add: function (name, desc) {
            console.log("add");
            if (_.isEmpty(name)) {
                msg.warning("Invalid name,try again!");
            } else {
                $.ajax({
                    url: "/app/add",
                    data: {'name': name, 'desc': desc},
                    success: function () {
                        msg.success("add app success!");
                        self.loadApps();
                    },
                    error: function (req, status, err) {
                        msg.error(req.responseJSON.message);
                    }
                });
                Data.setApp(null);
                group.clear();
            }
        },
        copy: function (name) {
            console.log("cp")
            if (_.isEmpty(name)) {
                msg.info("Invalid name,try again!");
            } else {
                $.post("/app/cp", {'srcName': Data.getApp().name, 'tarName': name}, function (e) {
                    msg.success("copy app success!");
                    self.loadApps();
                })
            }

        },
        edit: function (name, desc) {
            console.log("edit");
            if (_.isEmpty(name)) {
                msg.info("Invalid name,try again!");
            } else {
                $.ajax({
                    url: "/app/update",
                    data: {'appName': Data.getApp().name, 'name': name, 'desc': desc},
                    success: function () {
                        msg.success("update app success!");
                        self.loadApps();
                    },
                    error: function (req, status, err) {
                        msg.error(req.responseJSON.message);
                    }
                })
            }
        },
        pushAll: function () {
            if (confirm("推送可能导致对应应用重启，确认要推送全部配置吗？")) {
                $.post("/app/pushAll", {'key': ""}, function (e) {
                    msg.success("push all success!");
                })
            }
        },
        del: function (app) {
            if (confirm("删除不可恢复，确认要删除吗？")) {
                $.post("/app/del", {'appName': app.name}, function (e) {
                    $(".list-group .active").remove();
                    Data.setApp(null);
                    group.clear();
                })
            }
        },
        doExport: function () {
            $.get("/app/show/" + Data.getApp().name, function (d) {
                $("#modalExport .modal-body textarea").val(JSON.stringify(d));
                $("#modalExport").modal("show");
            })
        },
        doImport: function (appJsonStr) {
            try{
                var json = JSON.parse(appJsonStr);
            }catch (e){
                msg.error("parse JSON failed .")
                return ;
            }
            console.log(appJsonStr);
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                'type': 'POST',
                'url': "/app/import",
                'data': appJsonStr,
                'dataType': 'json',
                'success': function (flag) {
                    if (flag) {
                        msg.success("import success!");
                    } else {
                        msg.error("import failed!");
                    }
                },
                'error': function (err, textStatus) {
                    msg.error(err.responseJSON.message);
                }
            });
        },
        isValidApp: function (errmsg) {
            if (errmsg == undefined) {
                errmsg = "请选择要操作的目标。";
            }
            if (Data.getApp() == null) {
                msg.error(errmsg)
                return false;
            }
            return true;
        }
    };
});
