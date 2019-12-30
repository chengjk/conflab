define(['jquery', '_', 'Data', 'msg', 'mockdata', "etab"], function ($, _, Data, msg) {
    var self;
    return {
        init: function () {
            self = this;
            self.initView();
        },
        loadConfigs: function (groupName) {
            $("#tabConfig tbody").empty()
            $("#tabConfig").parent().removeClass("hidden");
            $("#tabGroup").parent().addClass("hidden");
            var url = "/conf/group/all";
            var data={app:Data.getApp().name,group:groupName}
            $.ajax({url:url,
                data:data,
                success:function (datas) {
                console.log(datas);
                $.get("temp/conftab.html", function (temp) {
                    var t = _.template(temp, {'variable': 'datas'});
                    var lr = t(datas);
                    $("#tabConfig tbody").html(lr);
                    $("#tabConfig").editableTableWidget();
                });
            }});
        },
        initView: function () {

            $("#tabConfig").delegate("tbody td", "click", function () {
                var tr = $(this).closest("tr");
                var key = _.trim(tr.find("td[name='key']").html());
                var value = _.trim(tr.find("td[name='value']").html());
                var desc = _.trim(tr.find("td[name='desc']").html());
                var c = {
                    'app': Data.getApp().name, 'group': Data.getGroup().name,
                    'key': key, 'value': value, 'desc': desc
                };
                Data.setConfig(c);
            });

            $("#tabConfig").delegate("tbody td", "change", function () {
                var tr = $(this).closest("tr");
                var confId = tr.data("id");
                var key = _.trim(tr.find("td[name='key']").html());
                var value = _.trim(tr.find("td[name='value']").html());
                var desc = _.trim(tr.find("td[name='desc']").html());
                var c = {
                    'app': Data.getApp().name, 'group': Data.getGroup().name,
                    'srcKey': Data.getConfig().key,
                    'key': key, 'value': value, 'desc': desc
                };
                self.edit(c);
            });

            $("#tabConfig").delegate("button", "click", function (e) {
                e.stopPropagation();
                var tr = $(this).closest("tr");
                self.del(tr);
            });
            var form = $("#tabConfig").next(".form-inline");

            form.find("button").off("click").click(function () {
                self.add(form);
            })
        },
        add: function (form) {
            console.log("add config");
            if (Data.getApp() != null && Data.getGroup() != null) {
                form.find("input[name=app]").val(Data.getApp().name);
                form.find("input[name=group]").val(Data.getGroup().name);

                $.ajax({
                    url: "/conf/add",
                    data: form.serialize(),
                    success: function () {
                        msg.success("add config success!");
                        form.find("input").val("");
                        self.loadConfigs(Data.getGroup().name);
                    },
                    error: function (req, status, err) {
                        msg.error(req.responseJSON.message);
                    }
                })

            } else {
                msg.info("请选择应用和组。");
            }
        },
        del: function (tr) {
            var key = tr.data("key");
            if (confirm("删除不可恢复，确认要删除吗？" + key)) {
                $.post("/conf/del", {'app': Data.getApp().name,'group':Data.getGroup().name,'key':key}, function (e) {
                    tr.remove();
                    Data.setConfig(null)
                })
            }
        },
        edit: function (config) {
            Data.setConfig(config);
            $.ajax({
                url: "/conf/update",
                data: config,
                success: function () {
                },
                error: function (req, status, err) {
                    msg.error(req.responseJSON.message);
                }
            })
        }
    }
});
