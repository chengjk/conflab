define(['jquery', 'mock'], function ($) {
    return false;
    //$.mockjaxSettings.contentType = "application/json";
    $.mockjaxSettings.responseTime = 1;
    //app start
    $.mockjax({
        url: "/app/key/*",
        status: 200,
        responseText: [{'id': 1, 'name': 'test', 'descp': 'test'},
            {'id': 2, 'name': 'test', 'descp': 'test'},
            {'id': 3, 'name': 'test', 'descp': 'test'}]
    });
    $.mockjax({
        url: "/app/del",
        status: 200,
        responseText: true
    });

    //group start
    $.mockjax({
        url: "/group/app/1",
        status: 200,
        responseText: [
            {'id': 1, 'name': 'test', 'appId': 1, 'descp': 'test'},
            {'id': 2, 'name': 'test', 'appId': 1, 'descp': 'test'},
            {'id': 3, 'name': 'test', 'appId': 1, 'descp': 'test'}]
    });
    $.mockjax({
        url: "/group/del",
        status: 200,
        responseText: true
    });


    //config start
    $.mockjax({
        url: "/conf/group/1",
        status: 200,
        responseText: [
            {'id': 1, 'groupId': 1, 'appId': 1, 'key': 'k1', 'value': 'v', 'descp': 'test'},
            {'id': 2, 'groupId': 2, 'appId': 1, 'key': 'k1', 'value': 'v', 'descp': 'test'},
            {'id': 3, 'groupId': 3, 'appId': 1, 'key': 'k1', 'value': 'v', 'descp': 'test'}]
    });
    $.mockjax({
        url: "/conf/del",
        status: 200,
        responseText: true
    });


});
