define(['jquery', 'mock'], function ($) {
    //$.mockjaxSettings.contentType = "application/json";
    $.mockjax({
        url: "/app/all",
        status: 200,
        responseText: [{'id': 1, 'name': 'test', 'descp': 'test'},
        { 'id': 2, 'name': 'test', 'descp': 'test' },
        {'id': 3, 'name': 'test', 'descp': 'test'}]
    });

    $.mockjax({
        url: "/group/app/1",
        status: 200,
        responseText: [
            {'id': 1, 'name': 'test', 'appId':1, 'descp': 'test'},
            {'id': 2, 'name': 'test', 'appId':1, 'descp': 'test' },
            {'id': 3, 'name': 'test', 'appId':1,'descp': 'test'} ]
    });
    $.mockjax({
        url: "/conf/group/1",
        status: 200,
        responseText: [
            {'id': 1, 'groupId': 1, 'appId':1, 'key': 'k1', 'value': 'v', 'descp': 'test'},
            {'id': 2, 'groupId': 2, 'appId':1, 'key': 'k1', 'value': 'v', 'descp': 'test' },
            {'id': 3, 'groupId': 3, 'appId':1, 'key': 'k1', 'value': 'v', 'descp': 'test'} ]
    });
});
