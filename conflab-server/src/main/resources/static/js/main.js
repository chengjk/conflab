require.config({
    baseUrl: 'js/',
    paths: {
        jquery: '../lib/jquery',
        mock:"../lib/jquery.mockjax",
        etab:'../lib/mindmup-editabletable'
    },
    shim:{
        mock:['jquery'],
        etab:['jquery']
    }
});
requirejs(['./index']);
