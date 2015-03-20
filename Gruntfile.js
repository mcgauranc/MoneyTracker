module.exports = function (grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        bower: {
            install: {
                options: {
                    install: true,
                    copy: false,
                    cleanTargetDir: true
                }
            }
        },
        karma: {
            options: {
                configFile: 'karma.conf.js'
            },
            unit: {
                singleRun: true
            },
            continuous: {
                singleRun: false,
                autoWatch: true
            }
        },
        wiredep: {
            task: {
                src: [
                    'web/src/main/resources/static/*.js',
                    'web/src/main/resources/static/components/**/*.js',
                    'web/src/main/resources/static/functionality/**/*.js',
                    'web/src/main/resources/static/vendors/**/*.min.js',
                    'web/src/main/resources/static/*.html'
                ],
                options: {}
            }
        },
        concat: {
            options: {
                // define a string to put between each file in the concatenated output
                separator: ';'
            },
            dist: {
                // the files to concatenate
                src: ['web/src/main/resources/**/*.js', '!web/src/main/vendors/**/*.js'],
                // the location of the resulting JS file
                dest: 'dist/<%= pkg.name %>.js'
            }
        },
        uglify: {
            options: {
                // the banner is inserted at the top of the output
                banner: '/*! <%= pkg.name %> <%= grunt.template.today("dd-mm-yyyy") %> */\n',
                mangle: false
            },
            dist: {
                files: {
                    'dist/<%= pkg.name %>.min.js': ['<%= concat.dist.dest %>']
                }
            }
        },
        jshint: {
            all: ['web/src/main/resources/static/*.js',
                'web/src/main/resources/static/components/**/*.js',
                'web/src/main/resources/static/functionality/**/*.js'],
            options: {
                // options here to override JSHint defaults
                globals: {
                    "angular": true                }
            }
        },
        watch: {
            files: ['<%= jshint.files %>'],
            tasks: ['jshint'],
            options: {
                atBegin: true
            }
        },
        connect: {
            server: {
                options: {
                    hostname: 'localhost',
                    port: 8080
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-uglify'); //This minifies all of the java script.
    grunt.loadNpmTasks('grunt-contrib-jshint'); //This validates code quality
    grunt.loadNpmTasks('grunt-contrib-watch'); //This will execute certain tasks when a file is modified.
    grunt.loadNpmTasks('grunt-contrib-concat'); //This will concatinate all of the JS files into one file.
    grunt.loadNpmTasks('grunt-contrib-connect'); //This will automatically run a webserver to test the application.
    grunt.loadNpmTasks('grunt-karma'); //allows us to exectute Karma from within Grunt.
    grunt.loadNpmTasks('grunt-wiredep'); //This will wire in all the javascript dependencies to the index.html.

    grunt.registerTask('test', ['jshint']);
    grunt.registerTask('default', ['jshint', 'concat', 'uglify']);
};