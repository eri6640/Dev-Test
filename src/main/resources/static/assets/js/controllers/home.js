var app = angular.module( 'DEVTEST', [] );
app.controller( 'HomeController', function( $scope, $rootScope, ResultsService ) {

    $rootScope.loadError = {
	show : false,
	message : ''
    };

    $rootScope.genError = {
	show : false,
	message : ''
    };

    $rootScope.prevResults = {};

    ResultsService.load();

    $scope.amount = 0;
    $scope.generate = ResultsService.generateNew;

} );

app.factory( 'ResultsService', function( $http, $rootScope ) {

    var load = function() {

	$rootScope.loadError.show = false;

	var res = $http.get( '/api/findAllSortedResults' );

	res.then( function( data, status, headers, config ) {

	    var body = data.data;

	    if ( body.success === false ) {
		console.log( 'Error: ' + body.message );
		$rootScope.loadError.show = true;
		$rootScope.loadError.message = body.message;
		$rootScope.results = 0;
	    }
	    else {
		$rootScope.prevResults = body.data;
		$rootScope.results = body.data.length;
	    }

	} );

    };

    var generateNew = function( amount ) {

	if ( isNaN( amount ) && !angular.isNumber( amount ) ) {
	    $rootScope.genError.show = true;
	    $rootScope.genError.message = 'Amount should be numeric!';
	}
	else if ( amount <= 0 ) {
	    $rootScope.genError.show = true;
	    $rootScope.genError.message = 'Amount should be positive value and greater than zero!';
	}
	else {

	    $rootScope.genError.show = false;

	    var res = $http.post( '/api/createNewResult', amount - 0 );

	    res.then( function( data, status, headers, config ) {

		var body = data.data;

		console.log( 'aa: ' + body.message );

		$rootScope.body = body;

		if ( body.success === false ) {
		    console.log( 'Error: ' + body.message );
		    $rootScope.genError.show = true;
		    $rootScope.genError.message = body.message;
		}
		else {
		    $rootScope.newResult = body.data;
		    load();
		}

	    } );

	}

    };

    return {
	load : load,
	generateNew : generateNew
    };

} );
