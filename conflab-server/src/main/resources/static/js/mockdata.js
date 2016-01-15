define(['jquery', 'mock'], function ($) {
    //$.mockjaxSettings.contentType = "application/json";
    $.mockjax({
        url: "/app/all",
        status: 200,
        responseText: [{'id': 1, 'name': 'test', 'descp': 'test'}, {
            'id': 2,
            'name': 'test',
            'descp': 'test'
        }, {'id': 3, 'name': 'test', 'descp': 'test'}]
    });
});
