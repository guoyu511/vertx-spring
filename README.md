# vertx-spring-web

## Overview


## How to use

#### Configure your spring xml

Add vertx namespace to your context xml and add router tag with special package name.

```
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:vertx="http://www.vertx.io/schema/vertx-spring-web"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
       http://www.springframework.org/schema/context
	   http://www.springframework.org/schema/context/spring-context-4.0.xsd
       http://www.vertx.io/schema/vertx-spring-web
       http://www.vertx.io/schema/vertx-spring-web.xsd">  
         
.....

<vertx:router base-package="[your package name to search routers]"></vertx:router>
```

#### Annotation based routers

1. Add @VertxRouter annotation on each handler classes
1. Add @RouterHandler annotation on each handler method

```
@VertxRouter
public class UserRouter {

...

    @Autowired UserService service;

...

    @RouterHandler(method = HttpMethod.GET, value = "/") // use non-blocking handler
    public void getUser(RoutingContext context) {
        // dosth with then write to response, shuld be asnyc
    }
    
    @RouterHandler(method = HttpMethod.GET, value = "/", blocking = true) // use bolcking handler
    public void getUserSync(RoutingContext context) {
        // dosth with then write to response, may be sync
    }
   
    
}
```

[Following sample code for more information](https://github.com/guoyu511/vertx-spring-web/blob/master/src/test/java/io/vertx/ext/spring/TestRouter.java)


## Launcher your application


