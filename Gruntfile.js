module.exports = function (grunt) {

    require("matchdep").filterDev("grunt-*").forEach(grunt.loadNpmTasks);

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        bower: {
            install: {
                options: {
                    install: true,
                    copy: true
                }
            }
        },
        karma: {
            options: {
                configFile: 'karma.conf.js'
            },
            unit: {
                background: true,
                singleRun: false
            }
        },
        wiredep: {
            target: {
                src: "web/src/main/resources/static/index.html",
                options: {}
            }
        },
        concat: {
            js: {
                options: {
                    stripBanners: true
                },
                src: ["web/src/main/resources/static/*.js",
                    "web/src/main/resources/static/components/**/*.js",
                    "web/src/main/resources/static/functionality/**/*.js",
                    "!web/src/main/resources/static/MoneyTracker.js",
                    "!**/*-test.js"],
                dest: "web/src/main/resources/static/<%= pkg.name %>.js"
            },
            css: {
                src: ["web/src/main/resources/static/*.css",
                    "web/src/main/resources/static/components/**/*.css",
                    "web/src/main/resources/static/functionality/**/*.css",
                    "!web/src/main/resources/static/MoneyTracker.css"],
                dest: 'web/src/main/resources/static/<%= pkg.name %>.css'
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
                    'web/src/main/resources/static/dist/<%= pkg.name %>.min.js': ['<%= concat.js.dest %>']
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
                    "angular": true
                }
            }
        },
        watch: {
            options: {
                atBegin: true
            },
            files: ["web/src/main/resources/static/*.js",
                "web/src/main/resources/static/index.html",
                "web/src/main/resources/static/components/**/*.js",
                "web/src/main/resources/static/functionality/**/*.js",
                "web/src/main/resources/static/components/**/*.css",
                "web/src/main/resources/static/functionality/**/*.css",
                "!web/src/main/resources/static/MoneyTracker.js",
                "!web/src/main/resources/static/MoneyTracker.css"],
            tasks: ["jshint", "concat"]
        },
        connect: {
            server: {
                options: {
                    hostname: 'localhost',
                    port: 9000,
                    base: 'web/src/main/resources/static/',
                    livereload: true,
                    open: true,
                    middleware: function (connect, options) {
                        if (!Array.isArray(options.base)) {
                            options.base = [options.base];
                        }

                        // Setup the proxy
                        var middlewares = [require('grunt-connect-proxy/lib/utils').proxyRequest];

                        // Serve static files.
                        options.base.forEach(function (base) {
                            middlewares.push(connect.static(base));
                        });

                        // Make directory browse-able.
                        var directory = options.directory || options.base[options.base.length - 1];
                        middlewares.push(connect.directory(directory));

                        return middlewares;
                    }
                },
                proxies: [
                    {
                        context: '/api',
                        host: 'localhost',
                        port: 8080
                    }
                ]
            }
        }
    });

    grunt.registerTask("server", ["configureProxies:server", "connect"]);
    grunt.registerTask("dev", ["concat", "karma", "server", "watch"]);
    grunt.registerTask("prod", ["wiredep", "jshint", "concat", "uglify"])
};