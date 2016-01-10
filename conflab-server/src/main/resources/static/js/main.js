require.config({
    baseUrl: 'js/',
    paths: {
        jquery: '../lib/jquery',
        etab:'../lib/mindmup-editabletable'
    }
});
requirejs(['./index']);
