require.config({
    baseUrl: 'js/apps/',
    paths: {
        jquery: '../../lib/jquery.min',
        '_':"../../lib/lodash.min",
        mock:"../../lib/jquery.mockjax",
        etab:'../../lib/mindmup-editabletable',
        alert:'../../lib/bootstrap/js/alert',
        modal:'../../lib/bootstrap/js/modal',
        Data:'../common/Data',
        mockdata:'../common/mockdata',
        msg:'../common/msgmgr'

    },
    shim:{
        mock:['jquery'],
        '_':['jquery'],
        etab:['jquery'],
        alert:['jquery'],
        modal:['jquery'],
        msg:['jquery']
    }
});
requirejs(['../index']);
