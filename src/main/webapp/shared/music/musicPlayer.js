/**
 * Created by gs on 29.04.2017.
 */
var commonApp = angular.module('commonApp');

commonApp.directive('musicPlayer', function(ngAudio, musicLoadService) {
    return {
        restrict: 'AEC',
        replace: true,
        templateUrl: 'shared/music/playerTemplate.html',
        link: function(scope, elem, attrs) {
            var promiseObj = musicLoadService.getSounds();
            promiseObj.then(function(value) {
                    scope.sounds = value;
                    scope.currentMusic = scope.sounds[0];
                    scope.currentMusicIndex = 0;

                    for (var i = 0; i < scope.sounds.length; i++){
                        scope.sounds[i].playing = false;
                        scope.sounds[i].loaded = false;
                        scope.sounds[i].sound = {};
                    }
                },
                function (value) {

                }
            );

            scope.file = {};

            var endedCallback = function () {
                scope.playNext();
            };

            scope.playSound = function (music, index) {
                if (music.loaded == false){
                    music.sound = ngAudio.load(music.path);
                    music.sound.setEndedCallback(endedCallback);
                    music.loaded = true;
                }

                if (scope.currentMusic != music){
                    scope.currentMusicIndex = index;
                    if (scope.currentMusic.loaded){
                        scope.currentMusic.sound.stop();
                        scope.currentMusic.playing = false;
                    }

                    scope.currentMusic = music;
                }

                if (!music.playing){
                    music.sound.play();
                }
                else {
                    music.sound.pause();
                }

                music.playing = !music.playing;
            };

            scope.muteVolume = function (music) {
                music.sound.muting = !music.sound.muting;
            };

            scope.changeVolume = function (music) {
                if (music.sound.muting){
                    music.sound.muting = false;
                }
            };

            scope.playNext = function () {
                scope.playSound(scope.sounds[(scope.currentMusicIndex + 1) % scope.sounds.length], (scope.currentMusicIndex + 1) % scope.sounds.length) ;
            };

            scope.playPrev = function () {
                if (scope.currentMusicIndex > 0){
                    scope.playSound(scope.sounds[(scope.currentMusicIndex - 1) % scope.sounds.length], (scope.currentMusicIndex - 1) % scope.sounds.length) ;
                }
            };

            scope.repeatMusic = function (music) {
                if (music.sound.loop === true){
                    music.sound.loop = 0;
                }
                else {
                    music.sound.loop = true;
                }
            };

            scope.setNewFile = function (newFile) {
                scope.file = newFile;
            };

            scope.closeAddMusicModal = function () {
                commonModule.hideAddMusicModal();
            };

            scope.uploadMusic = function () {
                var promiseObj = musicLoadService.uploadFile(scope.file, scope.fileName);
                promiseObj.then(function(value) {
                        var newSound = value;
                        newSound.playing = false;
                        newSound.loaded = false;
                        newSound.sound = {};
                        scope.sounds.push(newSound);
                        commonModule.hideAddMusicModal();
                    },
                    function (value) {

                    }
                );
            }
        },
        scope: {

        }
    };
});