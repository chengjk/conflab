require.config({
    baseUrl: 'js/apps/',
    paths: {
        jquery: '../../lib/jquery.min',
        '_':"../../lib/lodash.min",
        mock:"../../lib/jquery.mockjax",
        etab:'../../lib/mindmup-editabletable',
        alert:'../../lib/bootstrap/alert',
        modal:'../../lib/bootstrap/modal',
        Data:'../common/Data',
        mockdata:'../common/mockdata',
        msg:'../common/msgmgr'

    },
    shim:{
        mock:['jquery'],
        '_':['jquery'],
        etab:['jquery'],
        alert:['jquery'],
        msg:['jquery']
    }
});
requirejs(['../index']);
