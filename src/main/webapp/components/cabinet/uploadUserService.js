/**
 * Created by gs on 29.04.2017.
 */
var crapsApp = angular.module('crapsApp');

crapsApp.factory('userUploadService',['$q', 'Upload', function ($q, Upload) {
    return{
        uploadFile: function (file, name, surname, birthday) {
            var deferred = $q.defer();
            Upload.upload({
                url: '/controller?command=UPDATE',
                headers: {
                    'Content-Type': 'multipart/form-data; charset=utf-8'
                },
                fields: {
                    'name' : name,
                    'surname' : surname,
                    'birthday' : birthday
                },
                file: file
            }).then(function(response) {
                    deferred.resolve(response.data);
                },
                function(response) {
                    deferred.reject(response.status);
                }
            );

            return deferred.promise;
        }
    }
}]);