require.config({
    baseUrl: 'js/',
    paths: {
        jquery: '../lib/jquery.min',
        '_':"../lib/lodash.min",
        mock:"../lib/jquery.mockjax",
        etab:'../lib/mindmup-editabletable'
    },
    shim:{
        mock:['jquery'],
        '_':['jquery'],
        etab:['jquery']
    }
});
requirejs(['./index']);
