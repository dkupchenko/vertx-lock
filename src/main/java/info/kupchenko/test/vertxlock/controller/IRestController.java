package info.kupchenko.test.vertxlock.controller;


import io.vertx.reactivex.ext.web.Router;

public interface IRestController {

    void bind(Router router);

}
