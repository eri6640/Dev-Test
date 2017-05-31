
var app = angular.module( 'DEVTEST', [ 'ngRoute', 'oc.lazyLoad' ] );

app.constant( 'Const', {
    name : 'DEVTEST'
} );

app.config( function( $routeProvider, $httpProvider, $locationProvider, Const ) {

    $locationProvider.html5Mode( true );

    var homeRoute = {
	controller : 'HomeController',
	templateUrl : 'views/home.html',
	resolve : {
	    lazy : [ '$ocLazyLoad', function( $ocLazyLoad ) {
		return $ocLazyLoad.load( [ {
		    name : Const.name,
		    files : [ 'assets/js/controllers/home.js' ]
		} ] );
	    } ]
	}
    };

    $routeProvider.when( '/home', homeRoute );

    $routeProvider.otherwise( '/home', {
	redirectTo : '/home'
    } );

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    $httpProvider.defaults.headers.common['Accept'] = 'application/json';

} );